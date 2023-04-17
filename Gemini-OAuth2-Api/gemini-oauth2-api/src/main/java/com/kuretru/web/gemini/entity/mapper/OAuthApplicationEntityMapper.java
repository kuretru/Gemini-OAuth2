package com.kuretru.web.gemini.entity.mapper;

import com.kuretru.microservices.web.entity.mapper.BaseEntityMapper;
import com.kuretru.web.gemini.entity.data.OAuthApplicationDO;
import com.kuretru.web.gemini.entity.transfer.OAuthApplicationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OAuthApplicationEntityMapper extends BaseEntityMapper<OAuthApplicationDO, OAuthApplicationDTO> {

}
