package com.imeng.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.Enum.DelFlagEnum;
import com.imeng.common.model.Menu;
import com.imeng.common.model.PageResult;
import com.imeng.common.service.impl.SuperServiceImpl;
import com.imeng.web.mapper.MenuMapper;
import com.imeng.web.model.RoleMenu;
import com.imeng.web.service.IMenuService;
import com.imeng.web.service.IRoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 *
 * @author tw
 * @date 2020-01-14 16:59:18
 */
@Slf4j
@Service
public class MenuServiceImpl extends SuperServiceImpl<MenuMapper, Menu> implements IMenuService {

    private static final String CLOUMN_DELFLAG = "del_flag";

    @Autowired
    IRoleMenuService roleMenuService;

    /**
     * 列表
     * @param params
     * @return
     */
    @Override
    public PageResult<Menu> findList(Map<String, Object> params) {
        Page<Menu> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        List<Menu> list = baseMapper.findList(page, params);
        return PageResult.<Menu>builder().data(list).code(HttpStatus.OK.value()).count(page.getTotal()).build();
    }

    @Override
    public List<Menu> findMenusByRoleIds(Set<Long> roleIds, Integer type,Integer hidden,Integer delFlag) {
        return baseMapper.findMenusByRoleIds(roleIds,type,hidden,delFlag);
    }

    @Override
    public List<Menu> findMenusByRoleCodes(Set<String> roleCodes, Integer type,Integer hidden,Integer delFlag) {
        return baseMapper.findMenusByRoleCodes(roleCodes,type,hidden,delFlag);
    }

    @Override
    public void setMenuToRole(Long roleId, Set<Long> menuIds) {

        //移除roleId的菜单映射
        roleMenuService.removeByRoleId(roleId);
        //新建菜单的关联映射
        if (!CollectionUtils.isEmpty(menuIds)) {
            List<RoleMenu> roleMenus = new ArrayList<>(menuIds.size());
            menuIds.forEach(menuId -> roleMenus.add(new RoleMenu(roleId, menuId)));
            roleMenuService.saveBatch(roleMenus);
        }
    }

    @Override
    @Cacheable(value = "menu")
    public List<Menu> findAllMenus() {
        return baseMapper.selectList(new QueryWrapper<Menu>().eq(CLOUMN_DELFLAG, DelFlagEnum.NO_DEL.getDelFlag()));
    }

    @Override
    public List<Menu> findOnes() {
        return baseMapper.selectList(
                new QueryWrapper<Menu>()
                        .eq("type", 1)
                        .orderByAsc("sort")
        );
    }

    @Override
    @Cacheable(value = "role:menu",key = "#roleId",condition ="#roleId != null" )
    public List<Menu> findMenusByRoleId(Long roleId, Integer type, Integer hidden, Integer delFlag) {
        return baseMapper.findByRoleId(roleId,type,hidden,delFlag);
    }

}
