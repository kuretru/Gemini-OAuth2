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
public class OAuth2ApprovedDO implements Serializable {

    private String redirectUri;

    private String clientId;

    private UUID userId;

}
