package com.kuretru.web.gemini.entity.query;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
public class UserLoginQuery {

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String captcha;

}
