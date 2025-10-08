package com.kuretru.web.gemini.controller;

import com.kuretru.microservices.authentication.annotaion.RequireAuthorization;
import com.kuretru.microservices.authentication.constant.AccessTokenConstants;
import com.kuretru.microservices.authentication.context.AccessTokenContext;
import com.kuretru.microservices.authentication.entity.AccessTokenDTO;
import com.kuretru.microservices.common.constant.EmptyConstants;
import com.kuretru.microservices.oauth2.common.entity.GalaxyUserDTO;
import com.kuretru.microservices.oauth2.common.exception.OAuth2Exception;
import com.kuretru.microservices.oauth2.server.memory.OAuth2AccessTokenMemory;
import com.kuretru.microservices.web.constant.code.UserErrorCodes;
import com.kuretru.microservices.web.controller.BaseController;
import com.kuretru.microservices.web.entity.ApiResponse;
import com.kuretru.microservices.web.exception.ServiceException;
import com.kuretru.web.gemini.entity.query.UserLoginQuery;
import com.kuretru.web.gemini.entity.transfer.UserDTO;
import com.kuretru.web.gemini.entity.transfer.UserInformationDTO;
import com.kuretru.web.gemini.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@RestController
@RequestMapping("/api/users")
public class UserController extends BaseController {

    private final UserService service;
    private final OAuth2AccessTokenMemory oAuth2AccessTokenManager;

    @Autowired
    public UserController(UserService userService, OAuth2AccessTokenMemory oAuth2AccessTokenManager) {
        this.service = userService;
        this.oAuth2AccessTokenManager = oAuth2AccessTokenManager;
    }

    @GetMapping
    public ApiResponse<?> oauth2Get() throws ServiceException {
        String authorization = request.getHeader(AccessTokenConstants.AUTHORIZATION);
        if (!StringUtils.hasText(authorization)) {
            throw new ServiceException(UserErrorCodes.ACCESS_PERMISSION_ERROR, "请求头中AccessToken不存在");
        } else if (!authorization.startsWith("token ")) {
            throw new ServiceException(UserErrorCodes.ACCESS_PERMISSION_ERROR, "AccessToken格式不正确");
        }
        String token = authorization.replace("token ", "");
        try {
            UUID userId = oAuth2AccessTokenManager.verify(token, "");
            UserDTO userDTO = service.get(userId);
            GalaxyUserDTO result = new GalaxyUserDTO(userDTO.getId(), userDTO.getNickname(), userDTO.getAvatar());
            return ApiResponse.success(result);
        } catch (OAuth2Exception e) {
            throw new ServiceException(UserErrorCodes.ACCESS_PERMISSION_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @RequireAuthorization
    public ApiResponse<?> get(@PathVariable("id") UUID id) throws ServiceException {
        validUserId(id);
        UserDTO result = service.get(id);
        if (result == null) {
            throw ServiceException.build(UserErrorCodes.REQUEST_PARAMETER_ERROR, "指定资源不存在");
        }
        return ApiResponse.success(result);
    }

    @GetMapping("/{id}/information")
    @RequireAuthorization
    public ApiResponse<?> getInformation(@PathVariable("id") UUID id) throws ServiceException {
        validUserId(id);
        return ApiResponse.success(service.getInformation());
    }

    @PutMapping("/{id}/information")
    @RequireAuthorization
    public ApiResponse<?> updateInformation(@PathVariable("id") UUID id, @Validated @RequestBody UserInformationDTO record) throws ServiceException {
        validUserId(id);
        return ApiResponse.success(service.saveInformation(record));
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody UserLoginQuery record) throws ServiceException {
        return ApiResponse.success(service.login(record));
    }

    @PostMapping("/logout")
    @RequireAuthorization
    public ApiResponse<?> logout(@RequestBody AccessTokenDTO accessToken) {
        service.logout(accessToken.getId());
        return ApiResponse.success("已退出登录...");
    }

    private void validUserId(UUID id) throws ServiceException {
        if (id == null || EmptyConstants.EMPTY_UUID.equals(id)) {
            throw ServiceException.build(UserErrorCodes.REQUEST_PARAMETER_ERROR, "未指定ID或ID错误");
        }
        if (!id.equals(AccessTokenContext.getUserId())) {
            throw ServiceException.build(UserErrorCodes.REQUEST_PARAMETER_ERROR, "请勿操作别人的数据");
        }
    }

}
