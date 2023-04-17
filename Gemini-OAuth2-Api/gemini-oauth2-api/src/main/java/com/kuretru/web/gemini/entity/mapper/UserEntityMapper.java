package com.kuretru.web.gemini.entity.mapper;

import com.kuretru.microservices.web.entity.mapper.BaseEntityMapper;
import com.kuretru.web.gemini.entity.data.UserDO;
import com.kuretru.web.gemini.entity.transfer.UserDTO;
import com.kuretru.web.gemini.entity.transfer.UserInformationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper extends BaseEntityMapper<UserDO, UserDTO> {

    /**
     * 用户DO转用户基本信息DTO
     *
     * @param record 用户DO
     * @return 用户基本信息DTO
     */
    UserInformationDTO doToInformationDto(UserDO record);

    /**
     * 用户基本信息DTO转用户DO
     *
     * @param record 用户基本信息DTO
     * @return 用户DO
     */
    UserDO informationDtoToDo(UserInformationDTO record);

}
