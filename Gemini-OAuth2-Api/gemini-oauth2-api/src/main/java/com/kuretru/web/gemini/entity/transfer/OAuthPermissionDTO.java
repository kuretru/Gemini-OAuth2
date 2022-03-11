package com.kuretru.web.gemini.entity.transfer;

import com.kuretru.api.common.entity.transfer.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OAuthPermissionDTO extends BaseDTO {

    @NotNull
    private UUID applicationId;

    @NotNull
    private UUID userId;

    @NotNull
    private String permissions;

}
