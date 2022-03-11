package com.kuretru.web.gemini.entity.data;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kuretru.api.common.entity.data.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("oauth_application")
public class OAuthApplicationDO extends BaseDO {

    private String name;

    private String avatar;

    private String description;

    private String homepage;

    private String callback;

    private String clientId;

    private String clientSecret;

    private Instant secretCreateTime;

}
