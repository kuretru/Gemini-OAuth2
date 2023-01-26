package com.kuretru.web.gemini.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuretru.microservices.authentication.context.AccessTokenContext;
import com.kuretru.microservices.authentication.entity.AccessTokenBO;
import com.kuretru.microservices.authentication.entity.AccessTokenDTO;
import com.kuretru.microservices.authentication.entity.UserLoginDTO;
import com.kuretru.microservices.authentication.manager.AccessTokenManager;
import com.kuretru.microservices.web.constant.code.UserErrorCodes;
import com.kuretru.microservices.web.exception.ServiceException;
import com.kuretru.microservices.web.service.impl.BaseServiceImpl;
import com.kuretru.web.gemini.constant.RoleConstants;
import com.kuretru.web.gemini.entity.data.UserDO;
import com.kuretru.web.gemini.entity.query.UserLoginQuery;
import com.kuretru.web.gemini.entity.query.UserQuery;
import com.kuretru.web.gemini.entity.transfer.UserDTO;
import com.kuretru.web.gemini.mapper.UserMapper;
import com.kuretru.web.gemini.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, UserDO, UserDTO, UserQuery> implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final AccessTokenManager accessTokenManager;

    @Autowired
    public UserServiceImpl(UserMapper mapper, UserEntityMapper entityMapper, PasswordEncoder passwordEncoder, AccessTokenManager accessTokenManager) {
        super(mapper, entityMapper);
        this.passwordEncoder = passwordEncoder;
        this.accessTokenManager = accessTokenManager;
    }

    @Override
    public UserLoginDTO login(UserLoginQuery record) throws ServiceException {
        UserDO userDO = getByUsernameOrEmailOrMobile(record.getUsername());
        if (userDO == null) {
            throw ServiceException.build(UserErrorCodes.WRONG_USERNAME, "用户名错误");
        }
        if (!verifyPassword(record.getPassword(), userDO)) {
            throw ServiceException.build(UserErrorCodes.WRONG_PASSWORD, "密码错误");
        }

        userDO.setLastLogin(Instant.now());
        mapper.updateById(userDO);

        Set<String> roles = buildRoles(userDO);
        UUID userId = UUID.fromString(userDO.getUuid());
        AccessTokenDTO accessToken = accessTokenManager.generate(userId, roles);
        return new UserLoginDTO(userId, accessToken);
    }

    @Override
    public void logout(String accessTokenId) throws ServiceException {
        AccessTokenBO accessTokenBO = accessTokenManager.get(accessTokenId);
        UUID userId = AccessTokenContext.getUserId();
        if (!accessTokenBO.getUserId().equals(userId)) {
            throw new ServiceException(UserErrorCodes.ACCESS_PERMISSION_ERROR, "AccessTokenID与操作用户不匹配");
        }
        accessTokenManager.revoke(accessTokenId);
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
        return passwordEncoder.matches(password, record.getPassword());
    }

    private Set<String> buildRoles(UserDO userDO) {
        Set<String> result = new HashSet<>();
        if (userDO.getAdmin()) {
            result.add(RoleConstants.ADMIN);
        }
        return result;
    }

    @Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
    interface UserEntityMapper extends BaseServiceImpl.BaseEntityMapper<UserDO, UserDTO> {

    }

}
