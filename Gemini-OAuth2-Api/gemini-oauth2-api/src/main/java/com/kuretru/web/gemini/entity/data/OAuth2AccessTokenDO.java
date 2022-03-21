package com.kuretru.web.gemini.entity.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2AccessTokenDO implements Serializable {

    private UUID userId;

    private String refreshToken;

    private String scope;

}
