package com.kuretru.web.gemini.entity.view;

import lombok.Data;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
public class SystemStatisticsVO {

    private Long applicationCount;
    private Long userCount;
    private Long permissionCount;

}
