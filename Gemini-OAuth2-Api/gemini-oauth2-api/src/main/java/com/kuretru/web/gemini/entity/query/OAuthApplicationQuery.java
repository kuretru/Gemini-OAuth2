package com.kuretru.web.gemini.entity.query;

import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
public class OAuthApplicationQuery {

    @Size(max = 16)
    private String name;

}
