package com.kuretru.web.gemini.service;

import com.kuretru.web.gemini.entity.view.SystemStatisticsVO;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
public interface DashboardService {

    /**
     * 查看当前系统的统计信息
     *
     * @return 系统的统计信息
     */
    SystemStatisticsVO statistics();

}
