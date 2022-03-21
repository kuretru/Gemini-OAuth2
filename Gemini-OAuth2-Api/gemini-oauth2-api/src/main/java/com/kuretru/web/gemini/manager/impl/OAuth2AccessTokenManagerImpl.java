package com.kuretru.web.gemini.manager.impl;

import com.kuretru.api.common.entity.enums.OAuth2ErrorEnum;
import com.kuretru.api.common.entity.transfer.OAuth2AccessTokenDTO;
import com.kuretru.api.common.exception.OAuth2Exception;
import com.kuretru.api.common.util.StringUtils;
import com.kuretru.web.gemini.constant.OAuth2Constants;
import com.kuretru.web.gemini.entity.business.OAuth2AccessTokenBO;
import com.kuretru.web.gemini.entity.data.OAuth2AccessTokenDO;
import com.kuretru.web.gemini.manager.OAuth2AccessTokenManager;
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
public class OAuth2AccessTokenManagerImpl implements OAuth2AccessTokenManager {

    private static final String REDIS_ROOT_KEY = "OAuth2AccessTokenManager.";
    private static final String REDIS_ACCESS_TOKEN_KEY = REDIS_ROOT_KEY + "token.";
    private static final Duration ACCESS_TOKEN_EXPIRE_TIME = Duration.ofHours(2);
    private static final Integer ACCESS_TOKEN_EXPIRE_TIME_SECONDS = 2 * 60 * 60;

    private final RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    public OAuth2AccessTokenManagerImpl(RedisTemplate<String, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public OAuth2AccessTokenDTO.Response generate(UUID userId, String scope) {
        String accessToken = StringUtils.randomUUID();
        while (Boolean.TRUE.equals(redisTemplate.hasKey(key(accessToken)))) {
            accessToken = StringUtils.randomUUID();
        }

        OAuth2AccessTokenDO value = new OAuth2AccessTokenDO(userId, StringUtils.randomUUID(), scope);
        redisTemplate.opsForValue().set(key(accessToken), value, ACCESS_TOKEN_EXPIRE_TIME);
        return new OAuth2AccessTokenDTO.Response(accessToken, OAuth2Constants.TOKEN_TYPE,
                ACCESS_TOKEN_EXPIRE_TIME_SECONDS, value.getRefreshToken(), scope);
    }

    @Override
    public OAuth2AccessTokenBO get(String accessToken) throws OAuth2Exception {
        String key = key(accessToken);
        exist(key(accessToken));
        OAuth2AccessTokenDO value = (OAuth2AccessTokenDO)redisTemplate.opsForValue().get(key);
        assert value != null;
        return new OAuth2AccessTokenBO(value.getUserId(), value.getScope());
    }

    @Override
    public void refresh(String accessToken, String refreshToken) throws OAuth2Exception {

    }

    @Override
    public void revoke(String accessToken) throws OAuth2Exception {
        String key = key(accessToken);
        exist(key);
        redisTemplate.delete(key);
    }

    private void exist(String accessToken) throws OAuth2Exception {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(REDIS_ACCESS_TOKEN_KEY + accessToken))) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AccessTokenError.INVALID_GRANT, "AccessToken已过期");
        }
    }

    private String key(String accessToken) {
        return REDIS_ACCESS_TOKEN_KEY + accessToken;
    }

}
