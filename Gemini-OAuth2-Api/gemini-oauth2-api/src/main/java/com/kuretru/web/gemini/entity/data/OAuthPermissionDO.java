package com.kuretru.web.gemini.entity.data;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kuretru.api.common.entity.data.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * OAuth权限表
 *
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("oauth_permission")
public class OAuthPermissionDO extends BaseDO {

    /** 应用ID */
    private String applicationId;

    /** 用户ID */
    private String userId;

    /** 用户给应用授予的权限列表 */
    private String permissions;

}
