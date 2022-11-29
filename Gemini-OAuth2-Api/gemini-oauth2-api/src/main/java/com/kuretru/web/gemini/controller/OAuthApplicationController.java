package com.kuretru.web.gemini.controller;

import com.kuretru.microservices.authentication.annotaion.RequireAuthorization;
import com.kuretru.microservices.common.constant.EmptyConstants;
import com.kuretru.microservices.web.constant.code.UserErrorCodes;
import com.kuretru.microservices.web.controller.BaseRestController;
import com.kuretru.microservices.web.entity.ApiResponse;
import com.kuretru.microservices.web.exception.ServiceException;
import com.kuretru.web.gemini.entity.query.OAuthApplicationQuery;
import com.kuretru.web.gemini.entity.transfer.OAuthApplicationDTO;
import com.kuretru.web.gemini.service.OAuthApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@RestController
@RequestMapping("/oauth/applications")
@RequireAuthorization(hasRole = "admin")
public class OAuthApplicationController extends BaseRestController<OAuthApplicationService, OAuthApplicationDTO, OAuthApplicationQuery> {

    @Autowired
    public OAuthApplicationController(OAuthApplicationService service) {
        super(service);
    }

    @GetMapping("/{id}")
    @RequireAuthorization
    @Override
    public ApiResponse<OAuthApplicationDTO> get(@PathVariable("id") UUID id) throws ServiceException {
        return super.get(id);
    }

}
