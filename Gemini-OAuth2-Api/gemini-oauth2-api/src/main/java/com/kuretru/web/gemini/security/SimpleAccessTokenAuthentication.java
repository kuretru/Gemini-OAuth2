package com.kuretru.web.gemini.security;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
public class SimpleAccessTokenAuthentication implements Authentication {

    private String id;
    private String secret;

    private Set<SimpleGrantedAuthority> authorities;
    private boolean authenticated;

    /**
     * 用户主体的抽象，使用AccessToken ID
     *
     * @return Principal
     */
    @Override
    public Object getPrincipal() {
        return this.id;
    }

    /**
     * 用户的证明，这里使用AccessToken
     *
     * @return Credentials
     */
    @Override
    public Object getCredentials() {
        return this.secret;
    }

    /**
     * 可供业务层查看的用户的实际信息
     *
     * @return details
     */
    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

}
