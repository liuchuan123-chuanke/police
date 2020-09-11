package com.imeng.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import com.imeng.common.model.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.service.impl.SuperServiceImpl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;

import com.imeng.web.model.UserItem;
import com.imeng.web.mapper.UserItemMapper;
import com.imeng.web.service.IUserItemService;

/**
 * 用户item表 作为用户拓展字段
 *
 * @author tw
 * @date 2020-09-04
 */
@Slf4j
@Service
public class UserItemServiceImpl extends SuperServiceImpl<UserItemMapper, UserItem> implements IUserItemService {
    /**
     * 列表
     * @param params
     * @return
     */
    @Override
    public PageResult<UserItem> findList(Map<String, Object> params) {
        Page<UserItem> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        List<UserItem> list = baseMapper.findList(page, params);
        return PageResult.<UserItem>builder().data(list).code(0).count(page.getTotal()).build();
    }

    @Override
    public void updateByUserId(UserItem userItem) {
        baseMapper.update(userItem,new LambdaQueryWrapper<UserItem>().eq(UserItem::getUserId,userItem.getUserId()));
    }
}
