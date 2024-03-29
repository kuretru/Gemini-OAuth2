package com.kuretru.web.gemini.entity.transfer;

import com.kuretru.microservices.web.entity.transfer.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * OAuth应用DTO
 *
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OAuthApplicationDTO extends BaseDTO {

    @NotEmpty
    @Size(max = 16)
    private String name;

    @NotEmpty
    @Size(max = 128)
    private String avatar;

    @NotEmpty
    @Size(max = 128)
    private String description;

    @NotEmpty
    @Size(max = 64)
    private String homepage;

    @NotEmpty
    @Size(max = 64)
    private String callback;

}
