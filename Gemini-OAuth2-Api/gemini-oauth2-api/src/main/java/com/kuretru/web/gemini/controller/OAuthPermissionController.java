package com.kuretru.web.gemini.controller;

import com.kuretru.microservices.authentication.annotaion.RequireAuthorization;
import com.kuretru.microservices.authentication.context.AccessTokenContext;
import com.kuretru.microservices.common.utils.UuidUtils;
import com.kuretru.microservices.web.constant.code.UserErrorCodes;
import com.kuretru.microservices.web.controller.BaseController;
import com.kuretru.microservices.web.entity.ApiResponse;
import com.kuretru.microservices.web.entity.PaginationQuery;
import com.kuretru.microservices.web.entity.PaginationResponse;
import com.kuretru.microservices.web.exception.ServiceException;
import com.kuretru.web.gemini.constant.RoleConstants;
import com.kuretru.web.gemini.entity.query.OAuthPermissionQuery;
import com.kuretru.web.gemini.entity.view.OAuthPermissionVO;
import com.kuretru.web.gemini.service.OAuthPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@RestController
@RequestMapping("/api/oauth/permissions")
@RequireAuthorization
public class OAuthPermissionController extends BaseController {

    private final OAuthPermissionService service;

    @Autowired
    public OAuthPermissionController(OAuthPermissionService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<?> list(PaginationQuery paginationQuery, OAuthPermissionQuery query) throws ServiceException {
        if (PaginationQuery.isNull(paginationQuery)) {
            throw ServiceException.build(UserErrorCodes.REQUEST_PARAMETER_ERROR, "分页查询参数不存在");
        } else if (UuidUtils.isNotEmpty(query.getApplicationId()) && !AccessTokenContext.hasRoles(RoleConstants.ADMIN)) {
            // 只有管理员可以根据应用查找权限
            throw ServiceException.build(UserErrorCodes.ACCESS_PERMISSION_ERROR, "只有管理员具有根据应用查找的权限");
        } else if (UuidUtils.isNotEmpty(query.getUserId()) && !query.getUserId().equals(AccessTokenContext.getUserId())) {
            // 查找自己的权限时，横向鉴权
            throw ServiceException.build(UserErrorCodes.ACCESS_PERMISSION_ERROR, "请勿操作别人的数据");
        } else if (UuidUtils.isEmpty(query.getUserId()) && UuidUtils.isEmpty(query.getApplicationId())) {
            // 补充查询自己的查询参数
            query.setUserId(AccessTokenContext.getUserId());
        }

        PaginationResponse<OAuthPermissionVO> result = service.listVo(paginationQuery, query);
        if (result.getList() == null) {
            result.setList(new ArrayList<>());
        }
        if (result.getList().isEmpty()) {
            return ApiResponse.notFound(result);
        }
        return ApiResponse.success(result);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> remove(@PathVariable("id") UUID id) throws ServiceException {
        if (UuidUtils.isEmpty(id)) {
            throw ServiceException.build(UserErrorCodes.REQUEST_PARAMETER_ERROR, "未指定ID或ID错误");
        }
        service.remove(id);
        return ApiResponse.removed("资源已删除");
    }

}
