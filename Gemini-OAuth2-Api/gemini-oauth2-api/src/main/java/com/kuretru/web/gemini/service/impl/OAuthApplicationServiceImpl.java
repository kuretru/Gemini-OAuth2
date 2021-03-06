package com.kuretru.web.gemini.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuretru.microservices.web.service.impl.BaseServiceImpl;
import com.kuretru.web.gemini.entity.data.OAuthApplicationDO;
import com.kuretru.web.gemini.entity.query.OAuthApplicationQuery;
import com.kuretru.web.gemini.entity.transfer.OAuthApplicationDTO;
import com.kuretru.web.gemini.mapper.OAuthApplicationMapper;
import com.kuretru.web.gemini.service.OAuthApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Service
public class OAuthApplicationServiceImpl extends BaseServiceImpl<OAuthApplicationMapper, OAuthApplicationDO, OAuthApplicationDTO, OAuthApplicationQuery> implements OAuthApplicationService {

    @Autowired
    public OAuthApplicationServiceImpl(OAuthApplicationMapper mapper) {
        super(mapper, OAuthApplicationDO.class, OAuthApplicationDTO.class);
    }

    @Override
    public OAuthApplicationDTO getByClientId(String clientId) {
        QueryWrapper<OAuthApplicationDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("client_id", clientId);
        OAuthApplicationDO record = mapper.selectOne(queryWrapper);
        return doToDto(record);
    }

    @Override
    public String getClientSecret(String clientId) {
        QueryWrapper<OAuthApplicationDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("client_id", clientId);
        OAuthApplicationDO record = mapper.selectOne(queryWrapper);
        return record.getClientSecret();
    }

    @Override
    public boolean verifyRedirectUri(OAuthApplicationDTO record, String redirectUri) {
        if (StringUtils.hasText(redirectUri)) {
            // TODO 完整的校验逻辑
            return redirectUri.startsWith(record.getCallback());
        }
        return true;
    }

}
