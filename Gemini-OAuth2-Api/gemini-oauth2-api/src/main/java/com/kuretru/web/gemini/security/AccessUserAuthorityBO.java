package com.kuretru.web.gemini.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
public class AccessUserAuthorityBO implements GrantedAuthority {

    private String authority;

}
