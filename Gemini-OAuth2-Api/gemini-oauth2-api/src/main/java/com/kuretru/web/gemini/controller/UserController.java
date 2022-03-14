package com.kuretru.web.gemini.controller;

import com.kuretru.api.common.controller.BaseController;
import com.kuretru.api.common.entity.ApiResponse;
import com.kuretru.api.common.exception.ServiceException;
import com.kuretru.web.gemini.annotaion.RequireAuthorization;
import com.kuretru.web.gemini.entity.query.UserLoginQuery;
import com.kuretru.web.gemini.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService service;

    @Autowired
    public UserController(UserService userService) {
        this.service = userService;
    }

    @PostMapping("/login")
    public ApiResponse<?> login(UserLoginQuery record) throws ServiceException {
        return ApiResponse.success(service.login(record));
    }

    @PostMapping("/logout")
    @RequireAuthorization
    public ApiResponse<?> logout() {
        return ApiResponse.success("已退出登录...");
    }

}
