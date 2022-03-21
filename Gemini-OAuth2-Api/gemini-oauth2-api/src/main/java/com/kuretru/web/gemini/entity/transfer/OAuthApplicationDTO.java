package com.kuretru.web.gemini.entity.transfer;

import com.kuretru.api.common.entity.transfer.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OAuthApplicationDTO extends BaseDTO {

    @NotNull
    @Size(max = 16)
    private String name;

    @NotNull
    @Size(max = 128)
    private String avatar;

    @NotNull
    @Size(max = 128)
    private String description;

    @NotNull
    @Size(max = 64)
    private String homepage;

    @NotNull
    @Size(max = 64)
    private String callback;

}
