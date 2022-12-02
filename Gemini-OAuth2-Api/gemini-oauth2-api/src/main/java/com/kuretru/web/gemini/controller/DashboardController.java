package com.kuretru.web.gemini.controller;

import com.kuretru.microservices.authentication.annotaion.RequireAuthorization;
import com.kuretru.microservices.web.controller.BaseController;
import com.kuretru.microservices.web.entity.ApiResponse;
import com.kuretru.web.gemini.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@RestController
@RequestMapping("/dashboard")
@RequireAuthorization(hasRole = "admin")
public class DashboardController extends BaseController {

    private final DashboardService service;

    @Autowired
    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/statistics")
    public ApiResponse<?> statistics() {
        return ApiResponse.success(service.statistics());
    }

}
