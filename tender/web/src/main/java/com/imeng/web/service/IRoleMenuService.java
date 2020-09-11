package com.imeng.web.service;


import com.imeng.common.model.PageResult;
import com.imeng.common.service.ISuperService;
import com.imeng.web.model.RoleMenu;

import java.util.Map;

/**
 * 
 *
 * @author tw
 * @date 2020-01-14 16:59:18
 */
public interface IRoleMenuService extends ISuperService<RoleMenu> {
    /**
     * 列表
     * @param params
     * @return
     */
    PageResult<RoleMenu> findList(Map<String, Object> params);

    void removeByRoleId(Long roleId);
}

