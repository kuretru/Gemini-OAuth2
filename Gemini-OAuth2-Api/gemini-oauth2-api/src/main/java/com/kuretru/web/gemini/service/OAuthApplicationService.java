package com.kuretru.web.gemini.service;

import com.kuretru.api.common.service.BaseService;
import com.kuretru.web.gemini.entity.query.OAuthApplicationQuery;
import com.kuretru.web.gemini.entity.transfer.OAuthApplicationDTO;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
public interface OAuthApplicationService extends BaseService<OAuthApplicationDTO, OAuthApplicationQuery> {

    /**
     * 判断客户端传递过来的URI是否合法
     *
     * @param record      数据库中的记录
     * @param redirectUri 客户端传递过来的URI
     * @return 是否合法
     */
    boolean verifyRedirectUri(OAuthApplicationDTO record, String redirectUri);

}
