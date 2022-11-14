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
import com.kuretru.web.gemini.entity.query.OAuthPermissionQuery;
import com.kuretru.web.gemini.entity.transfer.OAuthPermissionDTO;
import com.kuretru.web.gemini.entity.view.OAuthPermissionVO;
import com.kuretru.web.gemini.mapper.OAuthPermissionMapper;
import com.kuretru.web.gemini.service.OAuthPermissionService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

    @Mapper(componentModel = "spring")
    interface OAuthPermissionEntityMapper extends BaseServiceImpl.BaseEntityMapper<OAuthPermissionDO, OAuthPermissionDTO> {

        @Override
        @Mapping(source = "uuid", target = "id")
        @Mapping(target = "permissions", expression = "java( com.kuretru.microservices.common.utils.StringUtils.stringToSet(record.getPermissions(), com.kuretru.web.gemini.service.impl.OAuthPermissionServiceImpl.PERMISSIONS_SEPARATOR) )")
        OAuthPermissionDTO doToDto(OAuthPermissionDO record);

        @Override
        @Mapping(source = "id", target = "uuid")
        @Mapping(target = "id", ignore = true)
        @Mapping(target = "createTime", ignore = true)
        @Mapping(target = "updateTime", ignore = true)
        @Mapping(target = "permissions", expression = "java( com.kuretru.microservices.common.utils.StringUtils.collectionToString(record.getPermissions(), com.kuretru.web.gemini.service.impl.OAuthPermissionServiceImpl.PERMISSIONS_SEPARATOR) )")
        OAuthPermissionDO dtoToDo(OAuthPermissionDTO record);

        @Mapping(source = "applicationId", target = "application.id")
        @Mapping(source = "applicationName", target = "application.name")
        @Mapping(source = "applicationAvatar", target = "application.avatar")
        @Mapping(source = "applicationDescription", target = "application.description")
        @Mapping(source = "applicationHomepage", target = "application.homepage")
        @Mapping(source = "userId", target = "user.id")
        @Mapping(source = "userNickname", target = "user.nickname")
        @Mapping(source = "userAvatar", target = "user.avatar")
        @Mapping(source = "uuid", target = "id")
        @Mapping(target = "permissions", expression = "java( com.kuretru.microservices.common.utils.StringUtils.stringToSet(record.getPermissions(), com.kuretru.web.gemini.service.impl.OAuthPermissionServiceImpl.PERMISSIONS_SEPARATOR) )")
        OAuthPermissionVO boToVo(OAuthPermissionBO record);

        List<OAuthPermissionVO> boToVo(List<OAuthPermissionBO> records);

    }

}
