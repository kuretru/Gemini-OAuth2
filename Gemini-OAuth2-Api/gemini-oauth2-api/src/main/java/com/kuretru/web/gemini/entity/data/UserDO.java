package com.kuretru.web.gemini.entity.data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kuretru.api.common.entity.data.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

/**
 * 用户表
 *
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("user")
public class UserDO extends BaseDO {

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 昵称 */
    private String nickname;

    /** 头像URL */
    private String avatar;

    /** 电子邮箱 */
    private String email;

    /** 手机号码 */
    private String mobile;

    /** 上一次登录的时间 */
    private Instant lastLogin;

    /** 是否是管理员 */
    @TableField("is_admin")
    private Boolean admin;

}
