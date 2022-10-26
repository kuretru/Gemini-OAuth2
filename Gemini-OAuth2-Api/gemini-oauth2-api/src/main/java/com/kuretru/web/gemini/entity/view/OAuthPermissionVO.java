package com.kuretru.web.gemini.entity.view;

import com.kuretru.microservices.web.entity.transfer.BaseDTO;
import lombok.*;

import java.util.Set;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OAuthPermissionVO extends BaseDTO {

    private OAuthApplicationVO application;

    private UserVO user;

    private Set<String> permissions;

}
