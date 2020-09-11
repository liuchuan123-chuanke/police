package com.imeng.web.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imeng.common.model.PageResult;
import com.imeng.common.model.Result;
import com.imeng.common.model.Role;
import com.imeng.common.service.ISuperService;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author tw
 * @date 2020-01-14 16:59:18
 */
public interface IRoleService extends ISuperService<Role> {
    /**
     * 列表
     * @param params
     * @return
     */
    PageResult<Role> findList(Map<String, Object> params);

    List<Role> queryRoleByUserId(Long userId);

    /**
     * 新增或更新角色
     *
     * @param role
     * @return Result
     */
    Result saveOrUpdateRole(Role role);

    /**
     * 根据role code 获取id
     * @param code
     * @return
     */
    Long getRoleIdByCode(String code);


    PageResult queryByParamWithPage(IPage<Role> page, String name, Integer status, Integer delFlag);

    /**
     * 按状态及删除标识查询校色
     * @param status
     * @param delFlag
     * @return
     */
    List<Role> queryRoleList(Integer status, Integer delFlag);
}

