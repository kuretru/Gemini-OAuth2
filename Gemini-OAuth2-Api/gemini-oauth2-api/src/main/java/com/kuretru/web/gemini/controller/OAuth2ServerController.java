package com.kuretru.web.gemini.controller;

import com.kuretru.api.common.controller.BaseController;
import com.kuretru.api.common.entity.enums.OAuth2ErrorEnum;
import com.kuretru.api.common.entity.transfer.OAuth2AuthorizeDTO;
import com.kuretru.api.common.exception.OAuth2Exception;
import com.kuretru.web.gemini.entity.transfer.OAuth2ApproveDTO;
import com.kuretru.web.gemini.manager.OAuth2ServerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
