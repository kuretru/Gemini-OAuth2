package com.kuretru.web.gemini.mapper;

import com.kuretru.web.gemini.entity.business.SystemStatisticsBO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Mapper
@Repository
public interface DashboardMapper {

    /**
     * 统计当前系统的的信息
     *
     * @return 当前系统的信息
     */
    SystemStatisticsBO selectSystemStatistics();

}
