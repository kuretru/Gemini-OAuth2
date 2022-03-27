package com.kuretru.web.gemini.manager.impl;

import com.kuretru.microservices.common.utils.StringUtils;
import com.kuretru.microservices.oauth2.common.entity.OAuth2AccessTokenDTO;
import com.kuretru.microservices.oauth2.common.entity.OAuth2AuthorizeDTO;
import com.kuretru.microservices.oauth2.common.entity.OAuth2ErrorEnum;
import com.kuretru.microservices.oauth2.common.exception.OAuth2Exception;
import com.kuretru.microservices.web.constant.code.UserErrorCodes;
import com.kuretru.microservices.web.exception.ServiceException;
import com.kuretru.web.gemini.constant.OAuth2Constants;
import com.kuretru.web.gemini.entity.transfer.OAuth2ApproveDTO;
import com.kuretru.web.gemini.entity.transfer.OAuthApplicationDTO;
import com.kuretru.web.gemini.manager.OAuth2ServerManager;
import com.kuretru.web.gemini.service.OAuthApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.Duration;
import java.util.UUID;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Service
public class OAuth2ServerManagerImpl implements OAuth2ServerManager {

    private static final String REDIS_ROOT_KEY = "OAuth2ServerManager.";
    private static final String REDIS_STATE_UNIQUE_KEY = REDIS_ROOT_KEY + "state.";
    private static final String REDIS_AUTHORIZE_KEY = REDIS_ROOT_KEY + "authorize.";
    private static final Duration AUTHORIZE_EXPIRE_TIME = Duration.ofMinutes(15);
    private final OAuthApplicationService applicationService;
    private final RedisTemplate<String, String> stringRedisTemplate;
    private final RedisTemplate<String, Serializable> serializableRedisTemplate;

    @Autowired
    public OAuth2ServerManagerImpl(OAuthApplicationService applicationService, RedisTemplate<String, String> stringRedisTemplate,
                                   RedisTemplate<String, Serializable> serializableRedisTemplate) {
        this.applicationService = applicationService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.serializableRedisTemplate = serializableRedisTemplate;
    }

    @Override
    public OAuth2ApproveDTO.Response authorize(OAuth2AuthorizeDTO.Request request) throws OAuth2Exception {
        // 判断State是否重复使用
        if (!OAuth2Constants.AUTHORIZATION_CODE_GRANT.equals(request.getResponseType())) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.UNSUPPORTED_RESPONSE_TYPE, "服务端仅支持授权码认证模式");
        }
        String stateKey = REDIS_STATE_UNIQUE_KEY + request.getState();
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(stateKey))) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.INVALID_REQUEST, "重复使用的State字段");
        }
        stringRedisTemplate.opsForValue().set(stateKey, request.getState(), AUTHORIZE_EXPIRE_TIME);

        // 判断ClientID是否合法
        OAuthApplicationDTO applicationDTO = applicationService.get(UUID.fromString(request.getClientId()));
        if (applicationDTO == null) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.INVALID_REQUEST, "指定的Client ID不存在");
        }
        if (!applicationService.verifyRedirectUri(applicationDTO, request.getRedirectUri())) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.INVALID_REQUEST, "redirect_uri不在预留信息的子集中");
        }

        // 向前端返回一次性Token
        String token = StringUtils.randomUUID();
        serializableRedisTemplate.opsForValue().set(REDIS_AUTHORIZE_KEY + token, request, AUTHORIZE_EXPIRE_TIME);
        return new OAuth2ApproveDTO.Response(applicationDTO.getId(), token, request.getScope());
    }

    @Override
    public OAuth2AuthorizeDTO.Response approve(OAuth2ApproveDTO.Request request) throws ServiceException {
        String key = REDIS_AUTHORIZE_KEY + request.getToken();
        if (Boolean.FALSE.equals(serializableRedisTemplate.hasKey(key))) {
            throw ServiceException.build(UserErrorCodes.REQUEST_PARAMETER_ERROR, "指定的Token已过期");
        }
        OAuth2AuthorizeDTO.Request authorizeDTO = (OAuth2AuthorizeDTO.Request)serializableRedisTemplate.opsForValue().getAndDelete(key);

        if (OAuth2ApproveDTO.Action.REJECT.equals(request.getAction())) {
//            throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.ACCESS_DENIED, "用户拒绝授权");
        }

        return null;
    }

    @Override
    public OAuth2AccessTokenDTO.Response accessToken(OAuth2AccessTokenDTO.Request request) throws OAuth2Exception {
        return null;
    }

}
