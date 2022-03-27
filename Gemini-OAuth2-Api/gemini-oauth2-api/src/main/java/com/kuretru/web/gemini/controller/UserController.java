package com.kuretru.web.gemini.controller;

import com.kuretru.microservices.web.constant.EmptyConstants;
import com.kuretru.microservices.web.constant.code.UserErrorCodes;
import com.kuretru.microservices.web.context.AccessTokenContext;
import com.kuretru.microservices.web.controller.BaseController;
import com.kuretru.microservices.web.entity.ApiResponse;
import com.kuretru.microservices.web.exception.ServiceException;
import com.kuretru.web.gemini.annotaion.RequireAuthorization;
import com.kuretru.web.gemini.entity.query.UserLoginQuery;
import com.kuretru.web.gemini.entity.transfer.UserDTO;
import com.kuretru.web.gemini.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping("/{id}")
    @RequireAuthorization
    public ApiResponse<?> get(@PathVariable("id") UUID id) throws ServiceException {
        if (id == null || EmptyConstants.EMPTY_UUID.equals(id)) {
            throw ServiceException.build(UserErrorCodes.REQUEST_PARAMETER_ERROR, "未指定ID或ID错误");
        }
        if (!id.equals(AccessTokenContext.getUserId())) {
            throw ServiceException.build(UserErrorCodes.REQUEST_PARAMETER_ERROR, "请勿操作别人的数据");
        }
        UserDTO result = service.get(id);
        if (null == result) {
            throw ServiceException.build(UserErrorCodes.REQUEST_PARAMETER_ERROR, "指定资源不存在");
        }
        return ApiResponse.success(result);
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody UserLoginQuery record) throws ServiceException {
        return ApiResponse.success(service.login(record));
    }

    @PostMapping("/logout")
    @RequireAuthorization
    public ApiResponse<?> logout() {
        return ApiResponse.success("已退出登录...");
    }

}
