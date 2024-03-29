package com.kuretru.web.gemini.service;

import com.kuretru.microservices.web.service.BaseService;
import com.kuretru.web.gemini.entity.query.OAuthApplicationQuery;
import com.kuretru.web.gemini.entity.transfer.OAuthApplicationDTO;
import com.kuretru.web.gemini.entity.view.OAuthApplicationSecretVO;

import java.util.UUID;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
public interface OAuthApplicationService extends BaseService<OAuthApplicationDTO, OAuthApplicationQuery> {

    /**
     * 查看应用密钥
     *
     * @param applicationId 应用ID
     * @return 应用密钥实体
     */
    OAuthApplicationSecretVO getSecret(UUID applicationId);

    /**
     * 生成新的应用密钥
     *
     * @param applicationId 应用ID
     * @return 应用密钥实体
     */
    OAuthApplicationSecretVO generateSecret(UUID applicationId);

    /**
     * 根据ClientId查询一条记录
     *
     * @param clientId ClientId
     * @return 一条记录，找不到时返回Null
     */
    OAuthApplicationDTO getByClientId(String clientId);

    /**
     * 根据ClientID查询ClientSecret
     *
     * @param clientId ClientId
     * @return ClientSecret
     */
    String getClientSecret(String clientId);

    /**
     * 判断客户端传递过来的URI是否合法
     *
     * @param record      数据库中的记录
     * @param redirectUri 客户端传递过来的URI
     * @return 是否合法
     */
    boolean verifyRedirectUri(OAuthApplicationDTO record, String redirectUri);

}
