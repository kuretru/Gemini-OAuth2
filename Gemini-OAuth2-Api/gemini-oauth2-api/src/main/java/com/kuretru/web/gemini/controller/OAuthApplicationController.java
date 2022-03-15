package com.kuretru.web.gemini.controller;

import com.kuretru.api.common.controller.BaseRestController;
import com.kuretru.web.gemini.annotaion.RequireAuthorization;
import com.kuretru.web.gemini.entity.query.OAuthApplicationQuery;
import com.kuretru.web.gemini.entity.transfer.OAuthApplicationDTO;
import com.kuretru.web.gemini.service.OAuthApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@RestController
@RequestMapping("/oauth2/applications")
@RequireAuthorization
public class OAuthApplicationController extends BaseRestController<OAuthApplicationService, OAuthApplicationDTO, OAuthApplicationQuery> {

    @Autowired
    public OAuthApplicationController(OAuthApplicationService service) {
        super(service);
    }

}
