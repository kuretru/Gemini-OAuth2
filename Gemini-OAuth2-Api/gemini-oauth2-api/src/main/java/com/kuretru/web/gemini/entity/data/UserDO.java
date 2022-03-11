package com.kuretru.web.gemini.entity.data;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kuretru.api.common.entity.data.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("user")
public class UserDO extends BaseDO {

    private String username;

    private String password;

    private String salt;

    private String nickname;

    private String avatar;

    private String email;

    private String mobile;

}
