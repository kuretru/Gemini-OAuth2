package com.kuretru.web.gemini.service.impl;

import com.kuretru.api.common.service.impl.BaseServiceImpl;
import com.kuretru.web.gemini.entity.data.OAuthApplicationDO;
import com.kuretru.web.gemini.entity.query.OAuthApplicationQuery;
import com.kuretru.web.gemini.entity.transfer.OAuthApplicationDTO;
import com.kuretru.web.gemini.mapper.OAuthApplicationMapper;
import com.kuretru.web.gemini.service.OAuthApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Service
public class OAuthApplicationServiceImpl extends BaseServiceImpl<OAuthApplicationMapper, OAuthApplicationDO, OAuthApplicationDTO, OAuthApplicationQuery> implements OAuthApplicationService {

    @Autowired
    public OAuthApplicationServiceImpl(OAuthApplicationMapper mapper) {
        super(mapper, OAuthApplicationDO.class, OAuthApplicationDTO.class);
    }

}
