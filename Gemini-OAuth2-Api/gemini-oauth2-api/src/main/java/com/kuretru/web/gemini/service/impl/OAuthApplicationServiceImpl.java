package com.kuretru.web.gemini.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuretru.microservices.web.exception.ServiceException;
import com.kuretru.microservices.web.service.impl.BaseServiceImpl;
import com.kuretru.web.gemini.entity.data.OAuthApplicationDO;
import com.kuretru.web.gemini.entity.mapper.OAuthApplicationEntityMapper;
import com.kuretru.web.gemini.entity.query.OAuthApplicationQuery;
import com.kuretru.web.gemini.entity.transfer.OAuthApplicationDTO;
import com.kuretru.web.gemini.entity.view.OAuthApplicationSecretVO;
import com.kuretru.web.gemini.mapper.OAuthApplicationMapper;
import com.kuretru.web.gemini.service.OAuthApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.UUID;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Service
public class OAuthApplicationServiceImpl
        extends BaseServiceImpl<OAuthApplicationMapper, OAuthApplicationDO, OAuthApplicationDTO, OAuthApplicationQuery>
        implements OAuthApplicationService {

    @Autowired
    public OAuthApplicationServiceImpl(OAuthApplicationMapper mapper, OAuthApplicationEntityMapper entityMapper) {
        super(mapper, entityMapper);
    }

    @Override
    public OAuthApplicationDTO save(OAuthApplicationDTO record) throws ServiceException {
        UUID uuid = UUID.randomUUID();
        while (get(uuid) != null) {
            uuid = UUID.randomUUID();
        }

        OAuthApplicationDO data = entityMapper.dtoToDo(record);
        addCreateTime(data, uuid);
        data.setClientId(generateClientId());
        mapper.insert(data);
        return get(data.getId());
    }

    @Override
    public OAuthApplicationSecretVO getSecret(UUID applicationId) {
        QueryWrapper<OAuthApplicationDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid", applicationId.toString());
        OAuthApplicationDO record = mapper.selectOne(queryWrapper);
        record.setClientSecret(desensitizationSecret(record.getClientSecret()));
        return ((OAuthApplicationEntityMapper)entityMapper).doToVo(record);
    }

    @Override
    public OAuthApplicationSecretVO generateSecret(UUID applicationId) {
        QueryWrapper<OAuthApplicationDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid", applicationId.toString());
        OAuthApplicationDO record = mapper.selectOne(queryWrapper);
        record.setClientSecret(UUID.randomUUID().toString());
        record.setSecretCreateTime(Instant.now());

        mapper.updateById(record);
        record = mapper.selectOne(queryWrapper);
        return ((OAuthApplicationEntityMapper)entityMapper).doToVo(record);
    }

    @Override
    public OAuthApplicationDTO getByClientId(String clientId) {
        QueryWrapper<OAuthApplicationDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("client_id", clientId);
        OAuthApplicationDO record = mapper.selectOne(queryWrapper);
        return entityMapper.doToDto(record);
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

    private String generateClientId() {
        String result = UUID.randomUUID().toString();
        result = result.replace("-", "");
        result = result.substring(12);
        return result;
    }

    private String desensitizationSecret(String secret) {
        if (StringUtils.isEmpty(secret)) {
            return secret;
        } else if (secret.length() == UUID.randomUUID().toString().length()) {
            // abcd****-****-****-****-********1234
            return secret.substring(0, 4)
                    + "****-****-****-****-********"
                    + secret.substring(32, 36);
        } else {
            return "";
        }
    }

}
