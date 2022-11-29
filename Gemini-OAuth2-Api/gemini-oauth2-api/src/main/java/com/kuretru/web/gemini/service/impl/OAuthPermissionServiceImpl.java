package com.kuretru.web.gemini.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kuretru.microservices.common.utils.StringUtils;
import com.kuretru.microservices.web.entity.PaginationQuery;
import com.kuretru.microservices.web.entity.PaginationResponse;
import com.kuretru.microservices.web.exception.ServiceException;
import com.kuretru.microservices.web.service.impl.BaseServiceImpl;
import com.kuretru.web.gemini.entity.business.OAuthPermissionBO;
import com.kuretru.web.gemini.entity.data.OAuthPermissionDO;
import com.kuretru.web.gemini.entity.query.OAuthPermissionQuery;
import com.kuretru.web.gemini.entity.transfer.OAuthPermissionDTO;
import com.kuretru.web.gemini.entity.view.OAuthApplicationVO;
import com.kuretru.web.gemini.entity.view.OAuthPermissionVO;
import com.kuretru.web.gemini.entity.view.UserVO;
import com.kuretru.web.gemini.mapper.OAuthPermissionMapper;
import com.kuretru.web.gemini.service.OAuthPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Service
public class OAuthPermissionServiceImpl extends BaseServiceImpl<OAuthPermissionMapper, OAuthPermissionDO, OAuthPermissionDTO, OAuthPermissionQuery> implements OAuthPermissionService {

    private static final String PERMISSIONS_SEPARATOR = ",";

    @Autowired
    public OAuthPermissionServiceImpl(OAuthPermissionMapper mapper) {
        super(mapper, OAuthPermissionDO.class, OAuthPermissionDTO.class);
    }

    @Override
    public OAuthPermissionDTO get(UUID applicationId, UUID userId) {
        QueryWrapper<OAuthPermissionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("application_id", applicationId.toString());
        queryWrapper.eq("user_id", userId.toString());
        OAuthPermissionDO record = mapper.selectOne(queryWrapper);
        return doToDto(record);
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
        List<OAuthPermissionVO> records = boToVo(page.getRecords());
        return new PaginationResponse<>(records, page.getCurrent(), page.getSize(), page.getTotal());
    }

    @Override
    protected OAuthPermissionDTO doToDto(OAuthPermissionDO record) {
        if (record == null) {
            return null;
        }
        OAuthPermissionDTO result = super.doToDto(record);
        result.setApplicationId(UUID.fromString(record.getApplicationId()));
        result.setUserId(UUID.fromString(record.getUserId()));
        result.setPermissions(StringUtils.stringToSet(record.getPermissions(), PERMISSIONS_SEPARATOR));
        return result;
    }

    @Override
    protected OAuthPermissionDO dtoToDo(OAuthPermissionDTO record) {
        if (record == null) {
            return null;
        }
        OAuthPermissionDO result = super.dtoToDo(record);
        result.setApplicationId(record.getApplicationId().toString());
        result.setUserId(record.getUserId().toString());
        result.setPermissions(StringUtils.collectionToString(record.getPermissions(), PERMISSIONS_SEPARATOR));
        return result;
    }

    private OAuthPermissionVO boToVo(OAuthPermissionBO record) {
        OAuthApplicationVO application = new OAuthApplicationVO();
        application.setId(UUID.fromString(record.getApplicationId()));
        application.setName(record.getApplicationName());
        application.setAvatar(record.getApplicationAvatar());
        application.setDescription(record.getApplicationDescription());
        application.setHomepage(record.getApplicationHomepage());

        UserVO user = new UserVO();
        user.setId(UUID.fromString(record.getUserId()));
        user.setNickname(record.getUserNickname());
        user.setAvatar(record.getUserAvatar());

        OAuthPermissionVO permission = new OAuthPermissionVO();
        permission.setId(UUID.fromString(record.getUuid()));
        permission.setApplication(application);
        permission.setUser(user);
        permission.setPermissions(StringUtils.stringToSet(record.getPermissions(), PERMISSIONS_SEPARATOR));
        return permission;
    }

    private List<OAuthPermissionVO> boToVo(List<OAuthPermissionBO> records) {
        if (records == null) {
            return null;
        }
        List<OAuthPermissionVO> result = new ArrayList<>(records.size());
        for (OAuthPermissionBO record : records) {
            result.add(boToVo(record));
        }
        return result;
    }

}
