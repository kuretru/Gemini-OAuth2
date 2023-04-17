package com.kuretru.web.gemini.entity.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kuretru.microservices.common.utils.InstantUtils;
import com.kuretru.microservices.web.entity.transfer.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OAuthApplicationSecretVO extends BaseDTO {

    private String clientId;

    private String clientSecret;

    @JsonFormat(pattern = InstantUtils.DEFAULT_DATE_FORMAT, timezone = InstantUtils.DEFAULT_ZONE)
    private Instant secretCreateTime;

}
