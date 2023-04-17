package com.kuretru.web.gemini.entity.mapper;

import com.kuretru.microservices.web.entity.mapper.BaseEntityMapper;
import com.kuretru.web.gemini.entity.data.OAuthApplicationDO;
import com.kuretru.web.gemini.entity.transfer.OAuthApplicationDTO;
import com.kuretru.web.gemini.entity.view.OAuthApplicationSecretVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OAuthApplicationEntityMapper extends BaseEntityMapper<OAuthApplicationDO, OAuthApplicationDTO> {

    /**
     * 将数据实体转换为密钥展示实体
     *
     * @param record 数据实体
     * @return 密钥展示实体
     */
    @Mapping(source = "uuid", target = "id")
    OAuthApplicationSecretVO doToVo(OAuthApplicationDO record);

}
