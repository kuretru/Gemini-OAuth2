package com.kuretru.web.gemini.entity.transfer;

import com.kuretru.api.common.entity.transfer.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OAuthApplicationDTO extends BaseDTO {

    @NotNull
    private String name;

    @NotNull
    private String avatar;

    @NotNull
    private String description;

    @NotNull
    private String homepage;

    @NotNull
    private String callback;

}
