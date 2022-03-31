package com.kuretru.web.gemini.manager;

import com.kuretru.microservices.oauth2.common.entity.OAuth2AccessTokenDTO;
import com.kuretru.microservices.oauth2.common.entity.OAuth2AuthorizeDTO;
import com.kuretru.microservices.oauth2.common.exception.OAuth2Exception;
import com.kuretru.microservices.web.exception.ServiceException;
import com.kuretru.web.gemini.entity.query.OAuth2ApproveQuery;
import com.kuretru.web.gemini.entity.transfer.OAuth2ApproveDTO;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
public interface OAuth2ServerManager {

    /**
     * 客户端请求身份认证
     *
     * @param record 请求参数实体
     * @return 重定向至前端的URL
     * @throws OAuth2Exception OAuth2认证异常，应以重定向的方式返回应用
     */
    String authorize(OAuth2AuthorizeDTO.Request record) throws OAuth2Exception;

    /**
     * 判断用户是否已批准该应用访问
     *
     * @param query 查询条件
     * @return 重定向至应用的URL
     * @throws ServiceException 未批准则抛出业务异常
     */
    String isApproved(OAuth2ApproveQuery query) throws ServiceException;

    /**
     * 用户批准访问该应用
     *
     * @param record 请求参数实体
     * @return 重定向至应用的URL
     * @throws OAuth2Exception OAuth2认证异常，应以重定向的方式返回应用
     */
    String approve(OAuth2ApproveDTO.Request record) throws OAuth2Exception;

    /**
     * 客户端请求AccessToken
     *
     * @param request 请求参数实体
     * @return 响应实体
     * @throws OAuth2Exception OAuth2认证异常，应以响应的方式返回应用
     */
    OAuth2AccessTokenDTO.Response accessToken(OAuth2AccessTokenDTO.Request request) throws OAuth2Exception;

}
