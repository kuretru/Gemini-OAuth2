package com.kuretru.web.gemini.entity.mapper;

import com.kuretru.web.gemini.entity.business.SystemStatisticsBO;
import com.kuretru.web.gemini.entity.view.SystemStatisticsVO;
import org.mapstruct.Mapper;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface DashboardEntityMapper {

    /**
     * 统计信息的BO转VO方法
     *
     * @param record BO
     * @return VO
     */
    SystemStatisticsVO statisticsBoToVo(SystemStatisticsBO record);


}
