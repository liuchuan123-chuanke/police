package com.imeng.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.model.PageResult;
import com.imeng.common.model.Result;
import com.imeng.common.model.Role;
import com.imeng.common.service.impl.SuperServiceImpl;
import com.imeng.web.mapper.RoleMapper;
import com.imeng.web.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author tw
 * @date 2020-01-14 16:59:18
 */
@Slf4j
@Service
public class RoleServiceImpl extends SuperServiceImpl<RoleMapper, Role> implements IRoleService {


    private static final String CLOUMN_CODE = "code";

    private static final String CLOUMN_NAME = "name";

    private static final String CLOUMN_STATUS = "status";
    /**
     * 列表
     * @param params
     * @return
     */
    @Override
    public PageResult<Role> findList(Map<String, Object> params) {
        Page<Role> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        List<Role> list = baseMapper.findList(page, params);
        return PageResult.<Role>builder().data(list).code(HttpStatus.OK.value()).count(page.getTotal()).build();
    }

    @Override
    public List<Role> queryRoleByUserId(Long userId) {
        return baseMapper.findRolesByUserId(userId);
    }



    @Override
    @CacheEvict(value = "role",allEntries = true)
    public Result saveOrUpdateRole(Role role) {

        if (role.getId() == null) {
            Assert.hasText(role.getCode(),"请指定角色的code");
            if(getRoleIdByCode(role.getCode())!=null){
               throw new IllegalArgumentException(role.getCode()+"已存在");
            }
            this.save(role);
        } else {
            role.setCode(null);
            baseMapper.updateById(role);
        }
        return Result.succeed(role);
    }

    /**
     * 按角色code查询角色id  便于多租户下的配置
     * @param code
     * @return
     */
    @Override
    public Long getRoleIdByCode(String code) {
        QueryWrapper query = new QueryWrapper();
        query.eq(CLOUMN_CODE,code);
        Role role = baseMapper.selectOne(query);
        return role!=null?role.getId():null;
    }

    /**
     * 查询角色列表
     * @param name
     * @return
     */
    @Override
    public PageResult queryByParamWithPage(IPage<Role> page, String name, Integer status, Integer delFlag) {
        QueryWrapper query = new QueryWrapper();
        if(!StringUtils.isEmpty(name)) {
            query.like(CLOUMN_NAME, name);
        }
        if(status!=null){
            query.eq(CLOUMN_STATUS,status);
        }
        if(delFlag!=null){
            query.eq(CLOUMN_DELFLG,delFlag);
        }
        page = baseMapper.selectPage(page,query);
        return PageResult.<Role>builder().code(200).count(page.getTotal()).data(page.getRecords()).build();
    }

    @Override
    public List<Role> queryRoleList(Integer status, Integer delFlag) {
        return baseMapper.selectList(new QueryWrapper<Role>().eq(CLOUMN_STATUS,status).eq(CLOUMN_DELFLG,delFlag));
    }

}
