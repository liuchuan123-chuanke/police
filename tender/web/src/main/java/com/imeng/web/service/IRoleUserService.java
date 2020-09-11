package com.imeng.web.service;

import com.imeng.common.model.PageResult;
import com.imeng.common.service.ISuperService;
import com.imeng.web.model.RoleUser;
import java.util.Map;

/**
 * 
 *
 * @author tw
 * @date 2020-01-08 20:17:58
 */
public interface IRoleUserService extends ISuperService<RoleUser> {
    /**
     * 列表
     * @param params
     * @return
     */
    PageResult<RoleUser> findList(Map<String, Object> params);

    RoleUser findByUserId(Long userId);

    void updateByUserId(RoleUser roleUser);
}

