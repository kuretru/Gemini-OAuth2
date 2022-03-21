package com.kuretru.web.gemini.manager;

import com.kuretru.api.common.entity.transfer.OAuth2AccessTokenDTO;
import com.kuretru.api.common.exception.OAuth2Exception;
import com.kuretru.web.gemini.entity.business.OAuth2AccessTokenBO;

import java.util.UUID;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
public interface OAuth2AccessTokenManager {

    /**
     * 生成一个AccessToken，并存入数据库
     *
     * @param userId 该AccessToken绑定的用户ID
     * @param scope  该AccessToken可访问的权限
     * @return AccessToken
     */
    OAuth2AccessTokenDTO.Response generate(UUID userId, String scope);

    /**
     * 根据AccessToken查询绑定的用户
     *
     * @param accessToken AccessToken
     * @return 绑定的用户
     * @throws OAuth2Exception OAuth2异常
     */
    OAuth2AccessTokenBO get(String accessToken) throws OAuth2Exception;

    /**
     * 刷新AccessToken
     *
     * @param accessToken  AccessToken
     * @param refreshToken RefreshToken
     * @throws OAuth2Exception OAuth2异常
     */
    void refresh(String accessToken, String refreshToken) throws OAuth2Exception;

    /**
     * 吊销指定AccessToken，从数据库中删除
     *
     * @param accessToken AccessToken
     * @throws OAuth2Exception OAuth2异常
     */
    void revoke(String accessToken) throws OAuth2Exception;

}
