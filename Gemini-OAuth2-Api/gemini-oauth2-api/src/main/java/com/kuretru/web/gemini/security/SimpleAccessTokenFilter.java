package com.kuretru.web.gemini.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
public class SimpleAccessTokenFilter extends OncePerRequestFilter {

    private final static String ACCESS_TOKEN_ID = "Access-Token-ID";
    private final static String ACCESS_TOKEN = "Access-Token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() == null || !context.getAuthentication().isAuthenticated()) {
            String accessTokenId = request.getHeader(ACCESS_TOKEN_ID);
            if (StringUtils.hasText(accessTokenId)) {
                Authentication authentication = obtainAccessToken(request, accessTokenId);
                context.setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    protected Authentication obtainAccessToken(HttpServletRequest request, String id) {
        SimpleAccessTokenAuthentication result = new SimpleAccessTokenAuthentication();
        result.setSecret(request.getHeader(ACCESS_TOKEN));
        return result;
    }

}
