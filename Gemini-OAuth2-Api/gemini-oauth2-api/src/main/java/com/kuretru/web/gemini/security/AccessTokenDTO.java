package com.kuretru.web.gemini.security;

import lombok.Data;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
public class AccessTokenDTO {

    private AccessTokenDTO() {

    }

    @Data
    static class Request {

        private String id;
        private Integer timestamp;
        private String nonce;
        private String signature;

    }

    @Data
    static class Response {

        private String id;
        private String secret;

    }

}
