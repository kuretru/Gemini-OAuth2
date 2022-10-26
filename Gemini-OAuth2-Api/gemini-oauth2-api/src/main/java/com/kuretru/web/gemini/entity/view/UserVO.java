package com.kuretru.web.gemini.entity.view;

import com.kuretru.microservices.web.entity.transfer.BaseDTO;
import lombok.*;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserVO extends BaseDTO {

    private String nickname;

    private String avatar;

}
