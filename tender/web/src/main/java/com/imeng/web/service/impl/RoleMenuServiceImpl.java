package com.imeng.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.Enum.DelFlagEnum;
import com.imeng.common.model.PageResult;
import com.imeng.common.service.impl.SuperServiceImpl;
import com.imeng.web.mapper.RoleMenuMapper;
import com.imeng.web.model.RoleMenu;
import com.imeng.web.service.IRoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
public class RoleMenuServiceImpl extends SuperServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    private static final String CLOUMN_ROLEID = "role_id";
    private static final String CLOUMN_MENUID = "menu_id";
    /**
     * 列表
     * @param params
     * @return
     */
    @Override
    public PageResult<RoleMenu> findList(Map<String, Object> params) {
        Page<RoleMenu> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        List<RoleMenu> list = baseMapper.findList(page, params);
        return PageResult.<RoleMenu>builder().data(list).code(HttpStatus.OK.value()).count(page.getTotal()).build();
    }

    @Override
    public void removeByRoleId(Long roleId) {
        QueryWrapper query = new QueryWrapper();
        query.eq(CLOUMN_ROLEID,roleId);
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setDelFlag(DelFlagEnum.DEL.getDelFlag());
        baseMapper.update(roleMenu,query);
    }
}
