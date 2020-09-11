package com.imeng.web.service;

import com.imeng.web.model.UserItem;
import com.imeng.common.model.PageResult;
import com.imeng.common.service.ISuperService;

import java.util.Map;

/**
 * 用户item表 作为用户拓展字段
 *
 * @author tw
 * @date 2020-09-04
 */
public interface IUserItemService extends ISuperService<UserItem> {
    /**
     * 列表
     * @param params
     * @return
     */
    PageResult<UserItem> findList(Map<String, Object> params);

    /**
     * 按用户id更新UserItem对象
     * @param userItem
     */
     void updateByUserId(UserItem userItem);
}

