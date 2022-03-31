package com.kuretru.web.gemini.controller;

import com.kuretru.microservices.authentication.annotaion.RequireAuthorization;
import com.kuretru.microservices.authentication.context.AccessTokenContext;
import com.kuretru.microservices.oauth2.common.entity.OAuth2AccessTokenDTO;
import com.kuretru.microservices.oauth2.common.entity.OAuth2AuthorizeDTO;
import com.kuretru.microservices.oauth2.common.exception.OAuth2Exception;
import com.kuretru.microservices.web.constant.code.ServiceErrorCodes;
import com.kuretru.microservices.web.controller.BaseController;
import com.kuretru.microservices.web.exception.ServiceException;
import com.kuretru.web.gemini.entity.query.OAuth2ApproveQuery;
import com.kuretru.web.gemini.entity.transfer.OAuth2ApproveDTO;
import com.kuretru.web.gemini.manager.OAuth2ServerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@RestController
@RequestMapping("/oauth2")
public class OAuth2ServerController extends BaseController {

    private final OAuth2ServerManager manager;

    @Autowired
    public OAuth2ServerController(OAuth2ServerManager manager) {
        this.manager = manager;
    }

    /**
     * 该请求由Application服务器重定向而来
     * Response应重定向回前端
     * 异常应重定向回Application服务器
     *
     * @param request Request
     * @throws IOException 重定向失败时，引发IO异常
     */
    @GetMapping("/authorize")
    public void authorize(@Validated OAuth2AuthorizeDTO.Request request) throws IOException {
        try {
            String redirectUrl = manager.authorize(request);
            response.sendRedirect(redirectUrl);
        } catch (OAuth2Exception e) {
            String redirectUrl = e.toRedirectUrl();
            response.sendRedirect(redirectUrl);
        }
    }

    /**
     * 前端查询用户是否已授权过，若已授权则直接重定向回Application服务器
     *
     * @param query 请求实体
     * @throws ServiceException 若未授权，则抛出异常，前端应提示用户授权
     */
    @GetMapping("/approve")
    public void isApproved(@Validated OAuth2ApproveQuery query) throws ServiceException {
        query.setUserId(AccessTokenContext.getUserId());
        String redirectUrl = manager.isApproved(query);
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            throw ServiceException.build(ServiceErrorCodes.SYSTEM_EXECUTION_ERROR, "重定向失败");
        }
    }

    /**
     * 该请求由前端调用而来
     * Response应重定向回Application服务器
     * 异常应重定向回Application服务器
     *
     * @param request 请求实体
     * @throws IOException 重定向失败时，引发IO异常
     */
    @PostMapping("/approve")
    @RequireAuthorization
    public void approve(@Validated @RequestBody OAuth2ApproveDTO.Request request) throws IOException {
        request.setUserId(AccessTokenContext.getUserId());
        try {
            String redirectUrl = manager.approve(request);
            response.sendRedirect(redirectUrl);
        } catch (OAuth2Exception e) {
            String redirectUrl = e.toRedirectUrl();
            response.sendRedirect(redirectUrl);
        }
    }

    /**
     * 该请求由Application服务器调用
     * Response应正常返回JSON数据
     * 异常应正常返回JSON数据
     *
     * @param request Request
     * @throws OAuth2Exception OAuth2异常
     */
    @PostMapping("/access_token")
    public OAuth2AccessTokenDTO.Response accessToken(@Validated @RequestBody OAuth2AccessTokenDTO.Request request) throws OAuth2Exception {
        return manager.accessToken(request);
    }

}
