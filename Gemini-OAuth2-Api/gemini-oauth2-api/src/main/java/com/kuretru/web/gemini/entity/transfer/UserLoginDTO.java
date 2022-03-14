package com.kuretru.web.gemini.entity.transfer;

import com.kuretru.api.common.entity.transfer.AccessTokenDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    @NotNull
    private String nickname;

    @NotNull
    private String avatar;

    @NotNull
    private AccessTokenDTO accessToken;

}
