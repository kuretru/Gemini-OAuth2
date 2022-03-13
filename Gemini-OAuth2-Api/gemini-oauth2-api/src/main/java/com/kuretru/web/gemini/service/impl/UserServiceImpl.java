package com.kuretru.web.gemini.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuretru.api.common.constant.code.UserErrorCodes;
import com.kuretru.api.common.exception.ServiceException;
import com.kuretru.api.common.service.impl.BaseServiceImpl;
import com.kuretru.web.gemini.entity.data.UserDO;
import com.kuretru.web.gemini.entity.query.UserLoginQuery;
import com.kuretru.web.gemini.entity.query.UserQuery;
import com.kuretru.web.gemini.entity.transfer.UserDTO;
import com.kuretru.web.gemini.entity.transfer.UserLoginDTO;
import com.kuretru.web.gemini.mapper.UserMapper;
import com.kuretru.web.gemini.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, UserDO, UserDTO, UserQuery> implements UserService {

    @Autowired
    public UserServiceImpl(UserMapper mapper) {
        super(mapper, UserDO.class, UserDTO.class);
    }

    @Override
    public UserLoginDTO login(UserLoginQuery record) throws ServiceException {
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("username", record.getUsername()).or()
                .eq("email", record.getUsername()).or()
                .eq("mobile", record.getUsername());
        UserDO user = mapper.selectOne(queryWrapper);

        if (user == null) {
            throw new ServiceException.Unauthorized(UserErrorCodes.WRONG_USERNAME, "该用户不存在");
        }

        return null;
    }

}
