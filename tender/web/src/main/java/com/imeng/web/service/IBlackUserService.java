package com.imeng.web.service;


import com.imeng.common.model.PageResult;
import com.imeng.common.service.ISuperService;
import com.imeng.web.model.BlackUser;

import java.util.Map;

/**
 * 
 *
 * @author tw
 * @date 2020-02-19 10:41:45
 */
public interface IBlackUserService extends ISuperService<BlackUser> {
    /**
     * 列表
     * @param params
     * @return
     */
    PageResult<BlackUser> findList(Map<String, Object> params);

    boolean isBlackUser(Long userId);
}

