package com.kuretru.web.gemini.entity.transfer;

import com.kuretru.microservices.web.entity.transfer.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

/**
 * OAuth权限DTO
 *
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OAuthPermissionDTO extends BaseDTO {

    @NotEmpty
    private UUID applicationId;

    @NotEmpty
    private UUID userId;

    @NotEmpty
    private Set<String> permissions;

}
