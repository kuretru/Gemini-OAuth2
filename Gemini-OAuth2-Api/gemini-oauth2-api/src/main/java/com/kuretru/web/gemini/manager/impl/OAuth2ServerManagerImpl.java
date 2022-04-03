package com.kuretru.web.gemini.manager.impl;

import com.kuretru.microservices.common.utils.StringUtils;
import com.kuretru.microservices.oauth2.common.constant.OAuth2Constants;
import com.kuretru.microservices.oauth2.common.entity.OAuth2AccessTokenDTO;
import com.kuretru.microservices.oauth2.common.entity.OAuth2AuthorizeDTO;
import com.kuretru.microservices.oauth2.common.entity.OAuth2ErrorEnum;
import com.kuretru.microservices.oauth2.common.entity.OAuth2Triple;
import com.kuretru.microservices.oauth2.common.exception.OAuth2Exception;
import com.kuretru.microservices.oauth2.server.manager.OAuth2AccessTokenManager;
import com.kuretru.microservices.oauth2.server.property.OAuth2ServerProperty;
import com.kuretru.microservices.web.constant.code.UserErrorCodes;
import com.kuretru.microservices.web.exception.ServiceException;
import com.kuretru.web.gemini.entity.data.OAuth2ApprovedDO;
import com.kuretru.web.gemini.entity.query.OAuth2ApproveQuery;
import com.kuretru.web.gemini.entity.transfer.OAuth2ApproveDTO;
import com.kuretru.web.gemini.entity.transfer.OAuthApplicationDTO;
import com.kuretru.web.gemini.entity.transfer.OAuthPermissionDTO;
import com.kuretru.web.gemini.manager.OAuth2ServerManager;
import com.kuretru.web.gemini.service.OAuthApplicationService;
import com.kuretru.web.gemini.service.OAuthPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.Duration;
import java.util.Set;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Service
public class OAuth2ServerManagerImpl implements OAuth2ServerManager {

    private static final String REDIS_ROOT_KEY = "OAuth2ServerManager.";
    private static final String REDIS_STATE_STATE_KEY = REDIS_ROOT_KEY + "state.";
    private static final String REDIS_TOKEN_KEY = REDIS_ROOT_KEY + "token.";
    private static final String REDIS_CODE_KEY = REDIS_ROOT_KEY + "code.";
    private static final Duration AUTHORIZE_EXPIRE_TIME = Duration.ofMinutes(15);

    private static final String APPROVE_URL = "/oauth/approve";

    private final OAuth2ServerProperty property;
    private final OAuthApplicationService applicationService;
    private final OAuthPermissionService permissionService;
    private final OAuth2AccessTokenManager accessTokenManager;
    private final RedisTemplate<String, String> stringRedisTemplate;
    private final RedisTemplate<String, Serializable> serializableRedisTemplate;

    @Autowired
    public OAuth2ServerManagerImpl(OAuthApplicationService applicationService, OAuthPermissionService permissionService,
                                   OAuth2AccessTokenManager accessTokenManager, OAuth2ServerProperty property,
                                   RedisTemplate<String, String> stringRedisTemplate, RedisTemplate<String, Serializable> serializableRedisTemplate) {
        this.property = property;
        this.applicationService = applicationService;
        this.permissionService = permissionService;
        this.accessTokenManager = accessTokenManager;
        this.stringRedisTemplate = stringRedisTemplate;
        this.serializableRedisTemplate = serializableRedisTemplate;
    }


    @Override
    public String authorize(OAuth2AuthorizeDTO.Request record) throws OAuth2Exception {
        // 判断ResponseType是否合法
        if (!OAuth2Constants.AUTHORIZATION_REQUEST_RESPONSE_TYPE.equals(record.getResponseType())) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.UNSUPPORTED_RESPONSE_TYPE, "服务端仅支持授权码认证模式");
        }

        // 判断State是否重复使用
        String stateKey = REDIS_STATE_STATE_KEY + record.getState();
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(stateKey))) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.INVALID_REQUEST, "重复使用的State字段");
        }
        stringRedisTemplate.opsForValue().set(stateKey, record.getState(), AUTHORIZE_EXPIRE_TIME);

        // 判断ClientID是否合法
        OAuthApplicationDTO applicationDTO = applicationService.getByClientId(record.getClientId());
        if (applicationDTO == null) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.INVALID_REQUEST, "指定的Client ID不存在");
        }

        // 判断RedirectUri是否合法
        if (!applicationService.verifyRedirectUri(applicationDTO, record.getRedirectUri())) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.INVALID_REQUEST, "redirect_uri不在预留信息的子集中");
        }

        // 生成返回给前端的Token
        String token = StringUtils.randomUUID();
        serializableRedisTemplate.opsForValue().set(REDIS_TOKEN_KEY + token, record, AUTHORIZE_EXPIRE_TIME);

        String redirectUrl = property.getFrontEndUrl() + APPROVE_URL + "?token=" + token + "&application_id=" + applicationDTO.getId();
        if (org.springframework.util.StringUtils.hasText(record.getScope())) {
            redirectUrl += "&scope=" + record.getScope();
        }
        return redirectUrl;
    }

    @Override
    public String isApproved(OAuth2ApproveQuery query) throws ServiceException {
        OAuthPermissionDTO permissionDTO = permissionService.get(query.getApplicationId(), query.getUserId());
        if (permissionDTO != null) {
            Set<String> scopes = StringUtils.stringToSet(query.getScope(), OAuth2Constants.SCOPES_SEPARATOR);
            if (permissionDTO.getPermissions().containsAll(scopes)) {
                try {
                    return authorized(query, true);
                } catch (OAuth2Exception e) {
                    throw ServiceException.build(UserErrorCodes.ACCESS_UNAUTHORIZED, e.getMessage());
                }
            }
        }
        throw ServiceException.build(UserErrorCodes.ACCESS_UNAUTHORIZED, "用户尚未授权");
    }

    @Override
    public String approve(OAuth2ApproveDTO.Request record) throws OAuth2Exception {
        if (OAuth2ApproveDTO.Action.APPROVE.equals(record.getAction())) {
            return authorized(record, false);
        }
        throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.ACCESS_DENIED, "用户拒绝授权");
    }

    @Override
    public OAuth2AccessTokenDTO.Response accessToken(OAuth2AccessTokenDTO.Request request) throws OAuth2Exception {
        if (!OAuth2Constants.ACCESS_TOKEN_REQUEST_GRANT_TYPE.equals(request.getGrantType())) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AccessTokenError.UNSUPPORTED_GRANT_TYPE, "服务端仅支持认证码方式");
        } else if (Boolean.FALSE.equals(serializableRedisTemplate.hasKey(REDIS_CODE_KEY + request.getCode()))) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AccessTokenError.UNAUTHORIZED_CLIENT, "授权请求已过期");
        }

        OAuth2ApprovedDO approvedDO = (OAuth2ApprovedDO)serializableRedisTemplate.opsForValue().getAndDelete(REDIS_CODE_KEY + request.getCode());
        assert approvedDO != null;
        if (approvedDO.getRedirectUri() == null) {
            if (request.getRedirectUri() != null) {
                throw new OAuth2Exception(OAuth2ErrorEnum.AccessTokenError.INVALID_REQUEST, "重定向地址不匹配");
            }
        } else if (!approvedDO.getRedirectUri().equals(request.getRedirectUri())) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AccessTokenError.INVALID_REQUEST, "重定向地址不匹配");
        } else if (!approvedDO.getClientId().equals(request.getClientId())) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AccessTokenError.INVALID_CLIENT, "客户端ID不匹配");
        }

        OAuthApplicationDTO applicationDTO = applicationService.getByClientId(approvedDO.getClientId());
        if (!request.getClientSecret().equals(applicationService.getClientSecret(request.getClientId()))) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AccessTokenError.INVALID_GRANT, "客户端密钥不匹配");
        }

        OAuth2Triple triple = new OAuth2Triple(applicationDTO.getId(), approvedDO.getUserId(), approvedDO.getScopes());
        return accessTokenManager.generate(triple);
    }

    /**
     * 进行授权操作
     *
     * @param record            参数实体
     * @param alreadyAuthorized 是否已授权
     * @return 重定向至应用的URL
     */
    private String authorized(OAuth2ApproveQuery record, boolean alreadyAuthorized) throws OAuth2Exception {
        String tokenKey = REDIS_TOKEN_KEY + record.getToken();
        if (Boolean.FALSE.equals(serializableRedisTemplate.hasKey(tokenKey))) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.INVALID_REQUEST, "授权请求已过期");
        }
        OAuth2AuthorizeDTO.Request request = (OAuth2AuthorizeDTO.Request)serializableRedisTemplate.opsForValue().getAndDelete(tokenKey);
        Set<String> scopes = StringUtils.stringToSet(record.getScope(), OAuth2Constants.SCOPES_SEPARATOR);

        // 若未授权，先写入授权记录
        if (!alreadyAuthorized) {
            OAuthPermissionDTO permissionDTO = permissionService.get(record.getApplicationId(), record.getUserId());
            try {
                if (permissionDTO != null) {
                    permissionDTO.getPermissions().addAll(scopes);
                    permissionService.update(permissionDTO);
                } else {
                    permissionDTO = new OAuthPermissionDTO();
                    permissionDTO.setApplicationId(record.getApplicationId());
                    permissionDTO.setUserId(record.getUserId());
                    permissionDTO.setPermissions(scopes);
                    permissionService.save(permissionDTO);
                }
            } catch (ServiceException e) {
                throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.INVALID_REQUEST, "写入授权记录失败");
            }
        }

        assert request != null;
        String redirectUri = request.getRedirectUri();
        OAuth2ApprovedDO approvedDO = new OAuth2ApprovedDO(redirectUri, request.getClientId(), record.getUserId(), scopes);
        String code = StringUtils.randomUUID();
        while (Boolean.TRUE.equals(serializableRedisTemplate.hasKey(REDIS_CODE_KEY + code))) {
            code = StringUtils.randomUUID();
        }
        serializableRedisTemplate.opsForValue().set(REDIS_CODE_KEY + code, approvedDO, AUTHORIZE_EXPIRE_TIME);

        if (!org.springframework.util.StringUtils.hasText(redirectUri)) {
            redirectUri = applicationService.getByClientId(request.getClientId()).getCallback();
        }

        return redirectUri + "?code=" + code + "&state=" + request.getState();
    }

}
