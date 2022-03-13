package com.kuretru.web.gemini.security;

import com.kuretru.api.common.entity.business.AccessTokenBO;
import com.kuretru.api.common.manager.AccessTokenManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
public class SimpleAccessTokenAuthenticationProvider implements AuthenticationProvider {

    private final AccessTokenManager accessTokenManager;

    public SimpleAccessTokenAuthenticationProvider(AccessTokenManager accessTokenManager) {
        this.accessTokenManager = accessTokenManager;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.isAuthenticated()) {
            return authentication;
        }
        String id = authentication.getPrincipal().toString();
//        AccessTokenBO accessTokenBO = accessTokenManager.get(id);

        String secret = authentication.getCredentials().toString();
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SimpleAccessTokenAuthentication.class.equals(authentication);
    }

}
