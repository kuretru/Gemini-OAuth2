package com.kuretru.web.gemini.entity.mapper;

import com.kuretru.microservices.web.entity.mapper.BaseEntityMapper;
import com.kuretru.web.gemini.entity.business.OAuthPermissionBO;
import com.kuretru.web.gemini.entity.data.OAuthPermissionDO;
import com.kuretru.web.gemini.entity.transfer.OAuthPermissionDTO;
import com.kuretru.web.gemini.entity.view.OAuthPermissionVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface OAuthPermissionEntityMapper extends BaseEntityMapper<OAuthPermissionDO, OAuthPermissionDTO> {

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
