package com.kuretru.web.gemini.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuretru.api.common.constant.code.UserErrorCodes;
import com.kuretru.api.common.entity.transfer.AccessTokenDTO;
import com.kuretru.api.common.exception.ServiceException;
import com.kuretru.api.common.manager.AccessTokenManager;
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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, UserDO, UserDTO, UserQuery> implements UserService {

    private final AccessTokenManager accessTokenManager;

    @Autowired
    public UserServiceImpl(UserMapper mapper, AccessTokenManager accessTokenManager) {
        super(mapper, UserDO.class, UserDTO.class);
        this.accessTokenManager = accessTokenManager;
    }

    @Override
    public UserLoginDTO login(UserLoginQuery record) throws ServiceException {
        UserDO userDO = getByUsernameOrEmailOrMobile(record.getUsername());
        if (userDO == null) {
            throw new ServiceException.Unauthorized(UserErrorCodes.WRONG_USERNAME, "用户名错误");
        }
        if (!verifyPassword(record.getPassword(), userDO)) {
            throw new ServiceException.Unauthorized(UserErrorCodes.WRONG_PASSWORD, "密码错误");
        }

        Set<String> roles = buildRoles(userDO);
        AccessTokenDTO accessToken = accessTokenManager.generate(UUID.fromString(userDO.getUuid()), roles);
        UserLoginDTO result = new UserLoginDTO();
        result.setNickname(userDO.getNickname());
        result.setAvatar(userDO.getAvatar());
        result.setAccessToken(accessToken);
        return result;
    }

    @Override
    public void logout() throws ServiceException {

    }

    private UserDO getByUsernameOrEmailOrMobile(String username) {
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("username", username)
                .or().eq("email", username)
                .or().eq("mobile", username);
        return mapper.selectOne(queryWrapper);
    }

    private boolean verifyPassword(String password, UserDO record) {
        return false;
    }

    private Set<String> buildRoles(UserDO userDO) {
        Set<String> result = new HashSet<>();
        if (userDO.getAdmin()) {
            result.add("admin");
        }
        return result;
    }

}
