package com.kuretru.web.gemini.manager.impl;

import com.kuretru.microservices.common.utils.StringUtils;
import com.kuretru.microservices.oauth2.common.constant.OAuth2Constants;
import com.kuretru.microservices.oauth2.common.entity.OAuth2AccessTokenDTO;
import com.kuretru.microservices.oauth2.common.entity.OAuth2AuthorizeDTO;
import com.kuretru.microservices.oauth2.common.entity.OAuth2ErrorEnum;
import com.kuretru.microservices.oauth2.common.entity.OAuth2Triple;
import com.kuretru.microservices.oauth2.common.exception.OAuth2Exception;
import com.kuretru.microservices.oauth2.server.entity.OAuth2ApproveDTO;
import com.kuretru.microservices.oauth2.server.entity.OAuth2ApproveQuery;
import com.kuretru.microservices.oauth2.server.entity.OAuth2ApprovedBO;
import com.kuretru.microservices.oauth2.server.memory.OAuth2AccessTokenMemory;
import com.kuretru.microservices.oauth2.server.memory.OAuth2UniqueCodeMemory;
import com.kuretru.microservices.oauth2.server.memory.OAuth2UniqueStateMemory;
import com.kuretru.microservices.oauth2.server.memory.OAuth2UniqueTokenMemory;
import com.kuretru.microservices.oauth2.server.property.OAuth2ServerProperty;
import com.kuretru.microservices.web.constant.code.UserErrorCodes;
import com.kuretru.microservices.web.exception.ServiceException;
import com.kuretru.web.gemini.entity.transfer.OAuthApplicationDTO;
import com.kuretru.web.gemini.entity.transfer.OAuthPermissionDTO;
import com.kuretru.web.gemini.manager.OAuth2ServerManager;
import com.kuretru.web.gemini.service.OAuthApplicationService;
import com.kuretru.web.gemini.service.OAuthPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Service
public class OAuth2ServerManagerImpl implements OAuth2ServerManager {

    private static final String APPROVE_URL = "/oauth/approve";

    private final OAuth2ServerProperty property;
    private final OAuth2AccessTokenMemory accessTokenMemory;
    private final OAuth2UniqueStateMemory uniqueStateMemory;
    private final OAuth2UniqueTokenMemory uniqueTokenMemory;
    private final OAuth2UniqueCodeMemory uniqueCodeMemory;
    private final OAuthApplicationService applicationService;
    private final OAuthPermissionService permissionService;

    @Autowired
    public OAuth2ServerManagerImpl(OAuth2ServerProperty property, OAuth2AccessTokenMemory accessTokenMemory,
                                   OAuth2UniqueStateMemory uniqueStateMemory, OAuth2UniqueTokenMemory uniqueTokenMemory,
                                   OAuth2UniqueCodeMemory uniqueCodeMemory,
                                   OAuthApplicationService applicationService, OAuthPermissionService permissionService) {
        this.property = property;
        this.accessTokenMemory = accessTokenMemory;
        this.uniqueStateMemory = uniqueStateMemory;
        this.uniqueTokenMemory = uniqueTokenMemory;
        this.uniqueCodeMemory = uniqueCodeMemory;
        this.applicationService = applicationService;
        this.permissionService = permissionService;
    }

    @Override
    public String authorize(OAuth2AuthorizeDTO.Request record) throws OAuth2Exception {
        // 判断ResponseType是否合法
        if (!OAuth2Constants.AUTHORIZATION_REQUEST_RESPONSE_TYPE.equals(record.getResponseType())) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.UNSUPPORTED_RESPONSE_TYPE, "服务端仅支持授权码认证模式");
        }

        // 判断State是否重复使用
        if (uniqueStateMemory.exist(record.getState())) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.INVALID_REQUEST, "重复使用的State字段");
        }

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
        String token = uniqueTokenMemory.generateAndSave(record);

        // 返回重定向至前端的URL
        String redirectUrl = property.getFrontEndUrl() + APPROVE_URL +
                "?token=" + token +
                "&application_id=" + applicationDTO.getId();
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
        }
        OAuth2ApprovedBO approvedDO = uniqueCodeMemory.getAndDelete(request.getCode());
        if (approvedDO == null) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AccessTokenError.UNAUTHORIZED_CLIENT, "授权请求已过期");
        }

        if (!org.springframework.util.StringUtils.hasText(approvedDO.getRedirectUri())) {
            if (org.springframework.util.StringUtils.hasText(request.getRedirectUri())) {
                throw new OAuth2Exception(OAuth2ErrorEnum.AccessTokenError.INVALID_REQUEST, "重定向地址不匹配");
            }
        } else if (!approvedDO.getRedirectUri().equals(request.getRedirectUri())) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AccessTokenError.INVALID_REQUEST, "重定向地址不匹配");
        } else if (!approvedDO.getClientId().equals(request.getClientId())) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AccessTokenError.INVALID_CLIENT, "客户端ID不匹配");
        }

        if (!request.getClientSecret().equals(applicationService.getClientSecret(request.getClientId()))) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AccessTokenError.INVALID_GRANT, "客户端密钥不匹配");
        }

        OAuthApplicationDTO applicationDTO = applicationService.getByClientId(approvedDO.getClientId());
        OAuth2Triple triple = new OAuth2Triple(applicationDTO.getId(), approvedDO.getUserId(), approvedDO.getScopes());
        return accessTokenMemory.generate(triple);
    }

    /**
     * 进行授权操作
     *
     * @param record            参数实体
     * @param alreadyAuthorized 是否已授权
     * @return 重定向至应用的URL
     */
    private String authorized(OAuth2ApproveQuery record, boolean alreadyAuthorized) throws OAuth2Exception {
        // 根据Token获取OAuth2认证请求
        OAuth2AuthorizeDTO.Request request = uniqueTokenMemory.getAndDelete(record.getToken());
        if (request == null) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.INVALID_REQUEST, "授权请求已过期");
        }
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

        // 生成返回给客户端的code凭据
        String redirectUri = request.getRedirectUri();
        OAuth2ApprovedBO approvedBO = new OAuth2ApprovedBO(redirectUri, request.getClientId(), record.getUserId(), scopes);
        String code = uniqueCodeMemory.generateAndSave(approvedBO);

        // 构造重定向地址
        if (!org.springframework.util.StringUtils.hasText(redirectUri)) {
            redirectUri = applicationService.getByClientId(request.getClientId()).getCallback();
        }
        return redirectUri + "?code=" + code + "&state=" + request.getState();
    }

}
