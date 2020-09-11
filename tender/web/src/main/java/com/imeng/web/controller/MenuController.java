package com.imeng.web.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;

import com.alibaba.fastjson.JSON;
import com.imeng.common.Enum.DelFlagEnum;
import com.imeng.common.Enum.MenuShowEnum;
import com.imeng.common.annotation.LoginUser;
import com.imeng.common.constant.CommonConstant;
import com.imeng.common.model.*;
import com.imeng.web.dto.MenuTree;
import com.imeng.web.dto.RoleMenuDto;
import com.imeng.web.model.RoleMenu;
import com.imeng.web.service.IMenuService;
import com.imeng.web.service.IRoleMenuService;
import com.imeng.web.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 
 *
 * @author tw
 * @date 2020-01-14 16:59:18
 */
@Slf4j
@RestController
@RequestMapping("/menu")
@Api(tags = "菜单服务Api")
public class MenuController {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IRoleMenuService roleMenuService;
    @Autowired
    private IRoleService roleService;


    /**
     * 新增or更新   同时清除缓存 新增
     */
    @ApiOperation(value = "新增或修改菜单")
    @PostMapping("/saveOrUpdate")
    @Caching(evict = {
            @CacheEvict(value = "menu",allEntries = true),
            @CacheEvict(value = "role:menu",allEntries = true)
    })
    public Result save(@RequestBody Menu menu) {
        if(menu.getId()==null) {
            //只有新增的时候添加pid 和pids
            Long pid = menu.getPId();
            if (pid == null||pid.intValue()==0) {
                menu.setPId(0l);
                menu.setPIds("");
            } else {
                Menu pMenu = menuService.getById(menu.getPId());
                if (pMenu == null) {
                    return Result.failed("错误的菜单信息");
                }
                String ppids = pMenu.getPIds() == null ? "" : pMenu.getPIds();
                menu.setPIds(ppids + "," + pMenu.getId());
            }
            log.info("新增菜单,父级id:{}", menu.getPId());
        }
        menuService.saveOrUpdate(menu);
        return Result.succeed("保存成功");
    }

    /**
     * 删除 实际是修改delFlag字段
     */
    @ApiOperation(value = "删除")
    @GetMapping("/del/{id}")
    public Result delete(@PathVariable Long id) {
        Menu entity = new Menu();
        entity.setId(id);
        entity.setDelFlag(1);
        menuService.updateById(entity);
        return Result.succeed("删除成功");
    }

    /**
     * 根据角色查询菜单树形结构
     * @param roleId
     * @return
     */
    @ApiOperation(value = "根据roleId获取对应的菜单")
    @GetMapping({"/{roleId}/menus","/menus"})
    public Result findMenusByRoleId(@PathVariable(required = false) Long roleId) {
        //获取该角色对应的菜单
        List<Menu> roleMenus = menuService.findMenusByRoleId(roleId,null, null,
                DelFlagEnum.NO_DEL.getDelFlag());
        //全部的菜单列表
        MenuTree tree = new MenuTree(roleMenus);
        return Result.succeed(tree.builTree());
    }

    /**
     * 根据roleCodes获取对应的权限
     * @param roleCodes
     * @return
     */
    @ApiOperation(value = "根据roleCodes获取对应的权限")
    @SuppressWarnings("unchecked")
    /*@Cacheable(value = "menu", key = "#roleCodes")*/
    @GetMapping("/{roleCodes}")
    public List<Menu> findMenuByRoles(@PathVariable String roleCodes) {
        List<Menu> result = null;
        if (StringUtils.isNotEmpty(roleCodes)) {
            Set<String> roleSet = (Set<String>) Convert.toCollection(HashSet.class, String.class, roleCodes);
            result = menuService.findMenusByRoleCodes(roleSet,null, MenuShowEnum.DISPLAY.getType(),
                    DelFlagEnum.NO_DEL.getDelFlag());
        }
        return result;
    }

    /**
     * 改变菜单角色对应关系
     * @return
     */
    @ApiOperation(value = "角色分配菜单")
    @PostMapping("/granted")
    @CacheEvict(value = "role:menu",key="#dto.roleId")
    public Result changeRoleMenu(@RequestBody RoleMenuDto dto){
        Assert.notNull(dto,"非法参数");
        Assert.notNull(dto.getRoleId(),"非法的角色Id");
        roleMenuService.removeByRoleId(dto.getRoleId());
        List<RoleMenu> roleMenus = new ArrayList<>();
        for(Long menuId:dto.getMenuIds()){
            roleMenus.add(new RoleMenu(dto.getRoleId(),menuId));
        }
        roleMenuService.saveBatch(roleMenus);
        return Result.succeed("成功");

    }

   /* *//**
     * 给角色分配菜单
     *//*
    @ApiOperation(value = "角色分配菜单")
    @PostMapping("/granted")
    public Result setMenuToRole(@RequestBody Menu menu) {
        menuService.setMenuToRole(menu.getRoleId(), menu.getMenuIds());
        return Result.succeed("操作成功");
    }*/


    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/findAlls")
    public Result findAlls() {
        List<Menu> list = menuService.findAllMenus();
        //全部的菜单列表
        MenuTree tree = new MenuTree(list);
        return Result.succeed(tree.builTree());
    }

    @ApiOperation(value = "获取菜单以及顶级菜单")
    @GetMapping("/findOnes")
    public PageResult<Menu> findOnes() {
        List<Menu> list = menuService.findOnes();
        return PageResult.<Menu>builder().data(list).code(0).count((long) list.size()).build();
    }


    /**
     * 当前登录用户的菜单
     *
     * @return
     */
    @GetMapping("/current")
    @ApiOperation(value = "查询当前用户菜单")
    public Result findMyMenu(@LoginUser(isFull = true) User user) {
        List<Role> roles = roleService.queryRoleByUserId(user.getId());

        if (CollectionUtil.isEmpty(roles)) {
            return Result.succeed(Collections.emptyList());
        }
        log.info(JSON.toJSONString(user));
        List<Menu> menus =
                menuService.findMenusByRoleCodes(roles.parallelStream().map(Role::getCode).collect(Collectors.toSet()),
                CommonConstant.MENU,MenuShowEnum.DISPLAY.getType(),DelFlagEnum.NO_DEL.getDelFlag());
        menus = new MenuTree(menus).builTree();
        return Result.succeed(menus);
    }

}
