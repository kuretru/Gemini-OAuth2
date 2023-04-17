package com.kuretru.web.gemini.entity.query;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
public class UserLoginQuery {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private String captcha;

}
