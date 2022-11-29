package com.kuretru.web.gemini.entity.query;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
public class OAuthPermissionQuery {

    @NotNull
    private UUID userId;

}
