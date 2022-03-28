package com.kuretru.web.gemini.controller;

import com.kuretru.microservices.authentication.context.AccessTokenContext;
import com.kuretru.microservices.oauth2.common.entity.OAuth2AccessTokenDTO;
import com.kuretru.microservices.oauth2.common.entity.OAuth2AuthorizeDTO;
import com.kuretru.microservices.oauth2.common.entity.OAuth2ErrorEnum;
import com.kuretru.microservices.oauth2.common.exception.OAuth2Exception;
import com.kuretru.microservices.web.constant.code.ServiceErrorCodes;
import com.kuretru.microservices.web.controller.BaseController;
import com.kuretru.microservices.web.exception.ServiceException;
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
     * @throws OAuth2Exception OAuth2异常
     */
    @GetMapping("/authorize")
    public void authorize(@Validated OAuth2AuthorizeDTO.Request request) throws OAuth2Exception {
        OAuth2ApproveDTO.Response result = manager.authorize(request);
        //TODO 待重构
        String url = String.format("/oauth2/approve?application_id=%s&token=%s&scope=%s",
                result.getApplicationId(), result.getToken(), result.getScope());
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new OAuth2Exception(OAuth2ErrorEnum.AuthorizeError.SERVER_ERROR, "服务端未能重定向");
        }
    }

    /**
     * 该请求由前端调用而来
     * Response应重定向回Application服务器
     * 异常应重定向回Application服务器
     *
     * @param request Request
     * @throws ServiceException OAuth2异常
     */
    @PostMapping("/approve")
    public void approve(@Validated @RequestBody OAuth2ApproveDTO.Request request) throws ServiceException {
        request.setUserId(AccessTokenContext.getUserId());
        OAuth2AuthorizeDTO.Response result = manager.approve(request);
        String url = String.format("%s?code=%s&state=%s", result.getCallback(), result.getCode(), result.getState());
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            throw ServiceException.build(ServiceErrorCodes.SYSTEM_EXECUTION_ERROR, "服务端未能重定向");
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
