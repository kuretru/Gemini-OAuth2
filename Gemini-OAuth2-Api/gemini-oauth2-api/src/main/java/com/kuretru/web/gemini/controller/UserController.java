package com.kuretru.web.gemini.controller;

import com.kuretru.api.common.controller.BaseController;
import com.kuretru.api.common.entity.ApiResponse;
import com.kuretru.api.common.exception.ServiceException;
import com.kuretru.web.gemini.entity.query.UserLoginQuery;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@RestController
@RequestMapping("/api/users")
public class UserController extends BaseController {

    @PostMapping("/login")
    public ApiResponse<?> login(UserLoginQuery record) throws ServiceException {
        return ApiResponse.success("ok");
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout() {
        return ApiResponse.success("已退出登录...");
    }

}
