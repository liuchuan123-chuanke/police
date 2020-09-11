package com.imeng.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.model.LoginAppUser;
import com.imeng.common.model.Result;
import com.imeng.common.model.User;
import com.imeng.common.model.base.BaseDto;
import com.imeng.common.service.ISuperService;
import com.imeng.web.dto.UserDto;
import com.imeng.web.vo.UserVo;

import java.util.List;

/**
 * 
 *
 * @author tw
 * @date 2020-01-07 15:30:46
 */
public interface IUserService extends ISuperService<User> {
    /**
     * 获取UserDetails对象
     *
     * @param username
     * @return
     */
    LoginAppUser findByUsername(String username);

    LoginAppUser findByMobile(String phone);

    LoginAppUser findByUId(Long userId);

    /**
     * 通过SysUser 转换为 LoginAppUser，把roles和permissions也查询出来
     *
     * @param user
     * @return
     */
    LoginAppUser getLoginAppUser(User user, boolean isSimple);

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    User selectByUsername(String username);

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     */
    User selectByMobile(String mobile);

    /**
     * 根据userId查询用户
     *
     * @param userId
     * @return
     */
    User selectByUserId(Long userId);

    /**
     * 按参数查询  当前支持 keyword 角色id 用户id等 公司id
     * @param dto
     * @return
     */
    Page<UserVo>  queryByParamDto(UserDto dto);


    /**
     * 按dto添加用户
     * @param dto
     */
    void  addOrUpdateUserByDto(UserDto dto);



}

