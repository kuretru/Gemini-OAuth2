package com.kuretru.web.gemini.entity.transfer;

import com.kuretru.microservices.web.entity.transfer.AccessTokenDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    @NotNull
    private UUID userId;

    @NotNull
    private AccessTokenDTO accessToken;

}
