package com.kuretru.web.gemini.controller;

import com.kuretru.api.common.controller.BaseRestController;
import com.kuretru.web.gemini.annotaion.RequireAuthorization;
import com.kuretru.web.gemini.entity.query.OAuthPermissionQuery;
import com.kuretru.web.gemini.entity.transfer.OAuthPermissionDTO;
import com.kuretru.web.gemini.service.OAuthPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@RestController
@RequestMapping("/oauth/permissions/")
@RequireAuthorization
public class OAuthPermissionController extends BaseRestController<OAuthPermissionService, OAuthPermissionDTO, OAuthPermissionQuery> {

    @Autowired
    public OAuthPermissionController(OAuthPermissionService service) {
        super(service);
    }

}
