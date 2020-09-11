package com.imeng.common.service;

import com.imeng.common.model.Role;
import com.imeng.common.model.User;

import java.util.List;

/**
 *
 * 基础user service类
 * @author wwj
 * 2020/6/2 14:21
 */
public interface UserBaseService {

    User findByUserId(Long userId);

    List<Role> queryRoleByUserId(Long userId);
}
