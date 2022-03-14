package com.kuretru.web.gemini.entity.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuretru.api.common.entity.transfer.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserDTO extends BaseDTO {

    @NotNull
    private String username;

    @NotNull
    private String nickname;

    @NotNull
    private String avatar;

    @NotNull
    private String email;

    @NotNull
    private String mobile;

    @NotNull
    @JsonProperty("isAdmin")
    private Boolean admin;

}
