package com.imeng.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.model.PageResult;
import com.imeng.common.service.impl.SuperServiceImpl;
import com.imeng.web.mapper.RoleUserMapper;
import com.imeng.web.model.RoleUser;
import com.imeng.web.service.IRoleUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author tw
 * @date 2020-01-08 20:17:58
 */
@Slf4j
@Service
public class RoleUserServiceImpl extends SuperServiceImpl<RoleUserMapper, RoleUser> implements IRoleUserService {

    private static final String CLOUMN_USERID = "user_id";
    /**
     * 列表
     * @param params
     * @return
     */
    @Override
    public PageResult<RoleUser> findList(Map<String, Object> params) {
        Page<RoleUser> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        List<RoleUser> list = baseMapper.findList(page, params);
        return PageResult.<RoleUser>builder().data(list).code(HttpStatus.OK.value()).count(page.getTotal()).build();
    }

    /**
     * 按UserId 查询用户角色
     * @param userId
     * @return
     */
    @Override
    public RoleUser findByUserId(Long userId) {
        QueryWrapper query = new QueryWrapper();
        query.eq(CLOUMN_USERID,userId);
        return baseMapper.selectOne(query);
    }

    @Override
    @CacheEvict(value = "role",key = "#roleUser.userId")
    public void updateByUserId(RoleUser roleUser) {
        QueryWrapper query = new QueryWrapper();
        query.eq(CLOUMN_USERID,roleUser.getUserId());
        baseMapper.update(roleUser,query);
    }
}
