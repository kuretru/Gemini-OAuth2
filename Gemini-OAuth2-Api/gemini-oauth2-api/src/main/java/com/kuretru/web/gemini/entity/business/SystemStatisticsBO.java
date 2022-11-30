package com.kuretru.web.gemini.entity.business;

import lombok.Data;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
public class SystemStatisticsBO {

    private Long applicationCount;
    private Long userCount;
    private Long permissionCount;

}
