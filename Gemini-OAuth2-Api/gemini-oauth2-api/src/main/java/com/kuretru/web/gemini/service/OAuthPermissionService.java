package com.kuretru.web.gemini.service;

import com.kuretru.microservices.web.service.BaseService;
import com.kuretru.web.gemini.entity.query.OAuthPermissionQuery;
import com.kuretru.web.gemini.entity.transfer.OAuthPermissionDTO;

import java.util.UUID;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
public interface OAuthPermissionService extends BaseService<OAuthPermissionDTO, OAuthPermissionQuery> {

    /**
     * 根据应用和用户查询一条记录
     *
     * @param applicationId 应用ID
     * @param userId        用户ID
     * @return 一条记录，找不到时返回Null
     */
    OAuthPermissionDTO get(UUID applicationId, UUID userId);

}
