package com.imeng.web.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.annotation.LoginUser;
import com.imeng.common.model.*;
import com.imeng.common.model.base.BaseDto;
import com.imeng.common.service.UserBaseService;
import com.imeng.common.utils.RegExpValidatorUtils;
import com.imeng.web.dto.UserDto;
import com.imeng.web.model.RoleUser;
import com.imeng.web.service.IRoleService;
import com.imeng.web.service.IRoleUserService;
import com.imeng.web.service.IUserService;
import com.imeng.web.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tw
 * @date 2020-01-07 15:30:46
 */
@Slf4j
@RestController
@RequestMapping("/users")
@Api(tags = "用户服务Api")
public class UserController  {

    private static final long adminRoleId = 1;
    private static final long nonAdminRoleId = 2;
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRoleUserService roleUserService;

    /**
     * 当前登录用户 LoginAppUser
     *
     * @return
     */
    @ApiOperation(value = "根据access_token当前登录用户")
    @GetMapping("/current")
    public Result<LoginAppUser> getLoginAppUser(@LoginUser(isFull = true) User user) {
        LoginAppUser loginAppUser = userService.getLoginAppUser(user,false);
        if(loginAppUser==null){
            return Result.failed("未知或受限制用户");
        }
        //把密码注释掉
        loginAppUser.setPwd("");
        return Result.succeed(loginAppUser);
    }


    /**
     * 用户列表查询
     * @param dto
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(value = "用户管理-用户列表")
    public PageResult userList(@RequestBody UserDto dto){
        Assert.notNull(dto,"非法参数");
        Assert.notNull(dto.getLimit(),"请指定页面大小");
        Assert.notNull(dto.getPage(),"请指定页码");
        Page<UserVo> userVoPage = userService.queryByParamDto(dto);
        return PageResult.<UserVo>builder().count(userVoPage.getTotal()).data(userVoPage.getRecords()).code(200).build();
    }

    /**
     * 添加或编辑用户信息
     * @param dto
     * @return
     */
    @PostMapping("/addOrUpdateUser")
    @ApiOperation(value = "添加或更新用户")
    public Result addOrUpdateUser(@RequestBody UserDto dto){
        Assert.notNull(dto,"非法参数");
        Assert.notBlank(dto.getNickName(),"昵称不能为空");
        Assert.notBlank(dto.getUserName(),"用户名不能为空");
        //用户名是否合法
        //Assert.isTrue(RegExpValidatorUtils.dto.getUserName(),"用户名不合法");
        Assert.notNull(dto.getOrgId(),"请选择公司");
        Assert.notNull(dto.getRoleId(),"请选择角色");
        if(StrUtil.isNotBlank(dto.getPassword())){
            Assert.isTrue(RegExpValidatorUtils.IsPasswordAvi(dto.getPassword()),"密码格式不合法");
        }
        userService.addOrUpdateUserByDto(dto);
        return Result.succeed();
    }

}
