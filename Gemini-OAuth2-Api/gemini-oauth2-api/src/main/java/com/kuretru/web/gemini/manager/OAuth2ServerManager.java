package com.kuretru.web.gemini.manager;

import com.kuretru.api.common.entity.transfer.OAuth2AccessTokenDTO;
import com.kuretru.api.common.entity.transfer.OAuth2AuthorizeDTO;
import com.kuretru.api.common.exception.OAuth2Exception;
import com.kuretru.web.gemini.entity.transfer.OAuth2ApproveDTO;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
public interface OAuth2ServerManager {

    /**
     * 客户端请求身份认证
     *
     * @param request 请求参数实体
     * @return 响应实体
     * @throws OAuth2Exception OAuth2认证异常
     */
    OAuth2ApproveDTO.Response authorize(OAuth2AuthorizeDTO.Request request) throws OAuth2Exception;

    /**
     * 客户端请求AccessToken
     *
     * @param request 请求参数实体
     * @return 响应实体
     * @throws OAuth2Exception OAuth2认证异常
     */
    OAuth2AccessTokenDTO.Response accessToken(OAuth2AccessTokenDTO.Request request) throws OAuth2Exception;

}
