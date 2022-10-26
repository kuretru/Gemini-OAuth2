package com.kuretru.web.gemini.entity.query;

import lombok.Data;

import java.util.UUID;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
public class OAuthPermissionQuery {

    private UUID applicationId;

    private UUID userId;

}
