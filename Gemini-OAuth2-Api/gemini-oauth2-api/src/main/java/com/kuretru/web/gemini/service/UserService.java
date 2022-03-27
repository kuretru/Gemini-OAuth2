package com.kuretru.web.gemini.service;

import com.kuretru.microservices.web.exception.ServiceException;
import com.kuretru.microservices.web.service.BaseService;
import com.kuretru.web.gemini.entity.query.UserLoginQuery;
import com.kuretru.web.gemini.entity.query.UserQuery;
import com.kuretru.web.gemini.entity.transfer.UserDTO;
import com.kuretru.web.gemini.entity.transfer.UserLoginDTO;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
public interface UserService extends BaseService<UserDTO, UserQuery> {

    /**
     * 用户登录
     *
     * @param record 登录请求实体
     * @return 登录响应实体
     * @throws ServiceException 登录失败时会产生异常
     */
    UserLoginDTO login(UserLoginQuery record) throws ServiceException;

    /**
     * 用户登出
     *
     * @throws ServiceException 未登录时会产生异常
     */
    void logout() throws ServiceException;

}
