package com.kuretru.web.gemini.manager.impl;

import com.kuretru.api.common.entity.enums.OAuth2ErrorEnum;
import com.kuretru.api.common.entity.transfer.OAuth2AccessTokenDTO;
import com.kuretru.api.common.entity.transfer.OAuth2AuthorizeDTO;
import com.kuretru.api.common.exception.OAuth2Exception;
import com.kuretru.api.common.util.StringUtils;
import com.kuretru.web.gemini.constant.OAuth2Constants;
import com.kuretru.web.gemini.entity.transfer.OAuth2ApproveDTO;
import com.kuretru.web.gemini.entity.transfer.OAuthApplicationDTO;
import com.kuretru.web.gemini.manager.OAuth2ServerManager;
import com.kuretru.web.gemini.service.OAuthApplicationService;
import lombok.AllArgsConstructor;
import lombok.Data;
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
        if (!OAuth2Constants.AUTHORIZATION_CODE_GRANT.equals(request.getResponseType())) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.UNSUPPORTED_RESPONSE_TYPE, "服务端仅支持授权码认证模式");
        }
        String stateKey = REDIS_STATE_UNIQUE_KEY + request.getState();
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(stateKey))) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.INVALID_REQUEST, "重复使用的State字段");
        }
        stringRedisTemplate.opsForValue().set(stateKey, request.getState(), AUTHORIZE_EXPIRE_TIME);

        OAuthApplicationDTO applicationDTO = applicationService.get(UUID.fromString(request.getClientId()));
        if (applicationDTO == null) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.INVALID_REQUEST, "指定的Client ID不存在");
        }
        if (!applicationService.verifyRedirectUri(applicationDTO, request.getRedirectUri())) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.INVALID_REQUEST, "redirect_uri不在预留信息的子集中");
        }

        AuthorizeDO authorizeDO = new AuthorizeDO(StringUtils.randomUUID(), request);
        serializableRedisTemplate.opsForValue().set(REDIS_AUTHORIZE_KEY + authorizeDO.token, authorizeDO, AUTHORIZE_EXPIRE_TIME);
        return new OAuth2ApproveDTO.Response(applicationDTO.getId(), authorizeDO.token, request.getScope());
    }

    @Override
    public OAuth2AccessTokenDTO.Response accessToken(OAuth2AccessTokenDTO.Request request) throws OAuth2Exception {
        return null;
    }

    @Data
    @AllArgsConstructor
    public static class AuthorizeDO implements Serializable {

        private String token;

        private OAuth2AuthorizeDTO.Request request;

    }

}
