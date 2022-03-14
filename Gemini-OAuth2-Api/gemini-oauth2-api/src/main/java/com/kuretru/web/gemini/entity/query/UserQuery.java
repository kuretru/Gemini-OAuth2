package com.kuretru.web.gemini.entity.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
public class UserQuery {

    private String username;

    private String nickname;

    private String email;

    private String mobile;

    @JsonProperty("isAdmin")
    private Boolean admin;

}
