package com.kuretru.web.gemini.manager;

import com.kuretru.microservices.oauth2.common.entity.OAuth2AccessTokenDTO;
import com.kuretru.microservices.oauth2.common.entity.OAuth2AuthorizeDTO;
import com.kuretru.microservices.oauth2.common.exception.OAuth2Exception;
import com.kuretru.microservices.web.exception.ServiceException;
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
     * 用户同意身份认证
     *
     * @param request 请求参数实体
     * @return 响应实体
     * @throws ServiceException 业务异常
     */
    OAuth2AuthorizeDTO.Response approve(OAuth2ApproveDTO.Request request) throws ServiceException;

    /**
     * 客户端请求AccessToken
     *
     * @param request 请求参数实体
     * @return 响应实体
     * @throws OAuth2Exception OAuth2认证异常
     */
    OAuth2AccessTokenDTO.Response accessToken(OAuth2AccessTokenDTO.Request request) throws OAuth2Exception;

}
