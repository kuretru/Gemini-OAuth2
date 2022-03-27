package com.kuretru.web.gemini.entity.data;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kuretru.microservices.web.entity.data.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

/**
 * OAuth应用表
 *
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("oauth_application")
public class OAuthApplicationDO extends BaseDO {

    /** 应用名称 */
    private String name;

    /** 应用头像URL */
    private String avatar;

    /** 应用描述 */
    private String description;

    /** 应用主页地址 */
    private String homepage;

    /** 回调地址 */
    private String callback;

    /** 客户端ID */
    private String clientId;

    /** 客户端Secret */
    private String clientSecret;

    /** 客户端Secret创建时间 */
    private Instant secretCreateTime;

}
