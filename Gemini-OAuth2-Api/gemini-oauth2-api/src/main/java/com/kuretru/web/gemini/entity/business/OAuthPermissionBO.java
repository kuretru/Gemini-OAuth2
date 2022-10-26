package com.kuretru.web.gemini.entity.business;

import lombok.Data;

import java.time.Instant;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Data
public class OAuthPermissionBO {

    private Long id;
    private String uuid;
    private Instant createTime;
    private Instant updateTime;
    private String applicationId;
    private String userId;
    private String permissions;
    private String applicationName;
    private String applicationAvatar;
    private String applicationDescription;
    private String applicationHomepage;
    private String userNickname;
    private String userAvatar;

}
