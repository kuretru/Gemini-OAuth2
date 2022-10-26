package com.kuretru.web.gemini.service;

import com.kuretru.microservices.web.entity.PaginationQuery;
import com.kuretru.microservices.web.entity.PaginationResponse;
import com.kuretru.microservices.web.exception.ServiceException;
import com.kuretru.microservices.web.service.BaseService;
import com.kuretru.web.gemini.entity.query.OAuthPermissionQuery;
import com.kuretru.web.gemini.entity.transfer.OAuthPermissionDTO;
import com.kuretru.web.gemini.entity.view.OAuthPermissionVO;

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

    /**
     * 根据查询条件，分页查询所有记录
     *
     * @param pagination 分页参数
     * @param query      查询条件
     * @return 符合查询条件，分页后的所有记录
     * @throws ServiceException 业务异常
     */
    PaginationResponse<OAuthPermissionVO> listVo(PaginationQuery pagination, OAuthPermissionQuery query) throws ServiceException;

}
