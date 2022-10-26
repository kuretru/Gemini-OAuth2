package com.kuretru.web.gemini.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kuretru.web.gemini.entity.business.OAuthPermissionBO;
import com.kuretru.web.gemini.entity.data.OAuthPermissionDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Mapper
@Repository
public interface OAuthPermissionMapper extends BaseMapper<OAuthPermissionDO> {

    /**
     * 查询权限管理表的关联实体列表
     *
     * @param page    分页参数
     * @param wrapper QueryWrapper
     * @return 关联实体列表
     */
    IPage<OAuthPermissionBO> listBo(IPage<OAuthPermissionBO> page, @Param(Constants.WRAPPER) Wrapper<OAuthPermissionBO> wrapper);

}
