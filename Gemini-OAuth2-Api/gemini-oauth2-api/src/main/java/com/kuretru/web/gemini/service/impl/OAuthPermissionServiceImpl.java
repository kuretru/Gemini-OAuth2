package com.kuretru.web.gemini.service.impl;

import com.kuretru.api.common.service.impl.BaseServiceImpl;
import com.kuretru.api.common.util.StringUtils;
import com.kuretru.web.gemini.entity.data.OAuthPermissionDO;
import com.kuretru.web.gemini.entity.query.OAuthPermissionQuery;
import com.kuretru.web.gemini.entity.transfer.OAuthPermissionDTO;
import com.kuretru.web.gemini.mapper.OAuthPermissionMapper;
import com.kuretru.web.gemini.service.OAuthPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    protected OAuthPermissionDTO doToDto(OAuthPermissionDO record) {
        if (record == null) {
            return null;
        }
        OAuthPermissionDTO result = super.doToDto(record);
        result.setApplicationId(UUID.fromString(record.getApplicationId()));
        result.setUserId(UUID.fromString(record.getUserId()));
        result.setPermissions(StringUtils.stringToList(record.getPermissions(), PERMISSIONS_SEPARATOR));
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

}
