package com.kuretru.web.gemini.entity.transfer;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户基本信息DTO
 *
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
public class UserInformationDTO {

    @NotNull
    private String nickname;

    @NotNull
    private String avatar;

}
