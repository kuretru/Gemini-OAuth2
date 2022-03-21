package com.kuretru.web.gemini.entity.transfer;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
public class OAuth2ApproveDTO {

    public enum Action {
        /** 同意授权 */
        APPROVE("approve"),
        /** 拒绝 */
        REJECT("reject");

        @JsonValue
        private final String value;

        Action(String value) {
            this.value = value;
        }

    }

    @Data
    public static class Request {

        /** 批准Token */
        @NotEmpty
        private String token;

        /** 授权的用户 */
        @NotNull
        private UUID userId;

        /** 授权的范围 */
        @NotEmpty
        private String scope;

        /** 授权动作 */
        @NotNull
        private Action action;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        /** 应用ID */
        @NotNull
        private UUID applicationId;

        /** 批准Token */
        @NotEmpty
        private String token;

        /** 授权的范围 */
        @NotEmpty
        private String scope;

    }

}
