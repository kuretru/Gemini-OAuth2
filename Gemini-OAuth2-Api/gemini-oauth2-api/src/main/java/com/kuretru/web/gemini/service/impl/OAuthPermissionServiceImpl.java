package com.kuretru.web.gemini.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kuretru.microservices.web.entity.PaginationQuery;
import com.kuretru.microservices.web.entity.PaginationResponse;
import com.kuretru.microservices.web.exception.ServiceException;
import com.kuretru.microservices.web.service.impl.BaseServiceImpl;
import com.kuretru.web.gemini.entity.business.OAuthPermissionBO;
import com.kuretru.web.gemini.entity.data.OAuthPermissionDO;
import com.kuretru.web.gemini.entity.mapper.OAuthPermissionEntityMapper;
import com.kuretru.web.gemini.entity.query.OAuthPermissionQuery;
import com.kuretru.web.gemini.entity.transfer.OAuthPermissionDTO;
import com.kuretru.web.gemini.entity.view.OAuthPermissionVO;
import com.kuretru.web.gemini.mapper.OAuthPermissionMapper;
import com.kuretru.web.gemini.service.OAuthPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Service
public class OAuthPermissionServiceImpl extends BaseServiceImpl<OAuthPermissionMapper, OAuthPermissionDO, OAuthPermissionDTO, OAuthPermissionQuery> implements OAuthPermissionService {

    public static final String PERMISSIONS_SEPARATOR = ",";

    @Autowired
    public OAuthPermissionServiceImpl(OAuthPermissionMapper mapper, OAuthPermissionEntityMapper entityMapper) {
        super(mapper, entityMapper);
    }

    @Override
    public OAuthPermissionDTO get(UUID applicationId, UUID userId) {
        QueryWrapper<OAuthPermissionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("application_id", applicationId.toString());
        queryWrapper.eq("user_id", userId.toString());
        OAuthPermissionDO record = mapper.selectOne(queryWrapper);
        return entityMapper.doToDto(record);
    }

    @Override
    public PaginationResponse<OAuthPermissionVO> listVo(PaginationQuery pagination, OAuthPermissionQuery query) throws ServiceException {
        QueryWrapper<OAuthPermissionBO> queryWrapper = new QueryWrapper<>();
        if (query.getApplicationId() != null) {
            queryWrapper.eq("application_id", query.getApplicationId().toString());
        }
        if (query.getUserId() != null) {
            queryWrapper.eq("user_id", query.getUserId().toString());
        }

        IPage<OAuthPermissionBO> page = new Page<>(pagination.getCurrent(), pagination.getPageSize());
        page = mapper.listBo(page, queryWrapper);
        List<OAuthPermissionVO> records = ((OAuthPermissionEntityMapper)entityMapper).boToVo(page.getRecords());
        return new PaginationResponse<>(records, page.getCurrent(), page.getSize(), page.getTotal());
    }

}
