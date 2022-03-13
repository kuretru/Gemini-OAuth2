package com.kuretru.web.gemini.security;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
public class SignatureAccessTokenFilter extends SimpleAccessTokenFilter {

    private final static String ACCESS_TIMESTAMP = "Access-Timestamp";
    private final static String ACCESS_NONCE = "Access-Nonce";
    private final static String ACCESS_SIGNATURE = "Access-Signature";

    @Override
    protected Authentication obtainAccessToken(HttpServletRequest request, String id) {
        // TODO obtain other headers
        return super.obtainAccessToken(request, id);
    }

}
