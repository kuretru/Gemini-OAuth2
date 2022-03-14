package com.kuretru.web.gemini.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kuretru.web.gemini.entity.data.OAuthPermissionDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Mapper
@Repository
public interface OAuthPermissionMapper extends BaseMapper<OAuthPermissionDO> {

}
