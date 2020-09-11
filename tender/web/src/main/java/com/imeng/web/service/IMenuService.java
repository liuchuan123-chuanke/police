package com.imeng.web.service;


import com.imeng.common.model.Menu;
import com.imeng.common.model.PageResult;
import com.imeng.common.service.ISuperService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 *
 * @author tw
 * @date 2020-01-14 16:59:18
 */
public interface IMenuService extends ISuperService<Menu> {
    /**
     * 列表
     * @param params
     * @return
     */
    PageResult<Menu> findList(Map<String, Object> params);

    List<Menu> findMenusByRoleIds(Set<Long> roleIds, Integer type, Integer hidden, Integer delFlag);

    List<Menu> findMenusByRoleCodes(Set<String> roleCodes, Integer type, Integer hidden, Integer delFlag);

    void setMenuToRole(Long roleId, Set<Long> menuIds);

    List<Menu> findAllMenus();

    List<Menu> findOnes();

    List<Menu> findMenusByRoleId(Long roleId, Integer type, Integer hidden, Integer delFlag);
}

