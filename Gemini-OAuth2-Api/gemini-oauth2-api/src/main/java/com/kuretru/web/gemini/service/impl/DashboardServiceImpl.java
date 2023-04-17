package com.kuretru.web.gemini.service.impl;

import com.kuretru.web.gemini.entity.business.SystemStatisticsBO;
import com.kuretru.web.gemini.entity.mapper.DashboardEntityMapper;
import com.kuretru.web.gemini.entity.view.SystemStatisticsVO;
import com.kuretru.web.gemini.mapper.DashboardMapper;
import com.kuretru.web.gemini.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    private final DashboardMapper mapper;
    private final DashboardEntityMapper entityMapper;

    @Autowired
    public DashboardServiceImpl(DashboardMapper mapper, DashboardEntityMapper entityMapper) {
        this.mapper = mapper;
        this.entityMapper = entityMapper;
    }

    @Override
    public SystemStatisticsVO statistics() {
        SystemStatisticsBO record = mapper.selectSystemStatistics();
        return entityMapper.statisticsBoToVo(record);
    }

}
