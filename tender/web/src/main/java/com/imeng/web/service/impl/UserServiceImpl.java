package com.imeng.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.Enum.DelFlagEnum;
import com.imeng.common.Enum.MenuShowEnum;
import com.imeng.common.constant.CommonConstant;
import com.imeng.common.lock.DistributedLock;
import com.imeng.common.model.*;
import com.imeng.common.service.UserBaseService;
import com.imeng.common.service.impl.SuperServiceImpl;
import com.imeng.redisStarter.template.RedisRepository;
import com.imeng.web.dto.UserDto;
import com.imeng.web.mapper.UserMapper;
import com.imeng.web.model.OrgInfo;
import com.imeng.web.model.RoleUser;
import com.imeng.web.model.UserItem;
import com.imeng.web.service.*;
import com.imeng.web.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tw
 * @date 2020-01-07 15:30:46
 */
@Slf4j
@Service
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements IUserService, UserBaseService {

    private static String CLOUMN_USERNAME = "user_name";

    private static String CLOUMN_EMAIL = "email1";


    @Autowired
    RedisRepository redisRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    IRoleUserService iRoleUserService;

    @Qualifier("taskExecutor")
    @Autowired
    TaskExecutor taskExecutor;


    @Autowired
    IRoleService iRoleService;

    @Autowired
    IMenuService iMenuService;

    @Autowired
    IOrgInfoService orgInfoService;
    @Autowired
    IUserItemService userItemService;

    @Autowired
    IBlackUserService iBlackUserService;

    @Autowired
    private DistributedLock distributedLock;


    /**
     * 按系统查询用户信息
     *
     * @param username
     * @return
     */
    @Override
    public LoginAppUser findByUsername(String username) {
        User user = selectByUsername(username);
        return getLoginAppUser(user,true);
    }



    /**
     * 按手机号查询用户信息 只支持phone1
     *
     * @param phone
     * @return
     */
    @Override
    public LoginAppUser findByMobile(String phone) {

        User user = selectByMobile(phone);
        return getLoginAppUser(user,true);
    }

    @Override
    public LoginAppUser findByUId(Long userId) {
        User user = selectByUserId(userId);
        return getLoginAppUser(user,true);
    }

    /**
     * 获取完整用户信息
     *
     * @param user
     * @return
     */
    @Override
    public LoginAppUser getLoginAppUser(User user,boolean isSimple) {

        if (user != null) {
            //校验是否在黑名单
            if (iBlackUserService.isBlackUser(user.getId())) {
                return null;
            }
            LoginAppUser loginAppUser = new LoginAppUser();
            BeanUtils.copyProperties(user, loginAppUser);

            List<Role> sysRoles = iRoleService.queryRoleByUserId(user.getId());
            // 设置角色
            loginAppUser.setRoles(sysRoles);

            if (!CollectionUtils.isEmpty(sysRoles)) {
                Set<Long> roleIds = sysRoles.parallelStream().map(SuperEntity::getId).collect(Collectors.toSet());
                List<Menu> menus = iMenuService.findMenusByRoleIds(roleIds, CommonConstant.PERMISSION,
                        MenuShowEnum.DISPLAY.getType(), DelFlagEnum.NO_DEL.getDelFlag());
                if (!CollectionUtils.isEmpty(menus)) {
                    Set<String> permissions = menus.parallelStream().map(p -> p.getPermession())
                            .collect(Collectors.toSet());
                    Set<String> urls = menus.parallelStream().map(p -> p.getUrl())
                            .collect(Collectors.toSet());
                    // 设置权限集合
                    loginAppUser.setUrls(urls);
                    loginAppUser.setPermissions(permissions);
                }
            }
            return loginAppUser;
        }
        return null;
    }



    /**
     * 按用户名查询用户
     *
     * @param username
     * @return
     */
    @Override
    public User selectByUsername(String username) {
        List<User> list = baseMapper.selectByUserParam(username, null, null, null);
        return getUser(list);
    }

    @Override
    public User selectByMobile(String mobile) {
        List<User> list = baseMapper.selectByUserParam(null,  mobile, null, null);
        return getUser(list);
    }

    @Override
    @Cacheable(value="user")
    public User selectByUserId(Long userId) {
        List<User> list = baseMapper.selectByUserParam(null, null, null, userId);
        User user = getUser(list);
        return user;
    }

    @Override
    public Page<UserVo> queryByParamDto(UserDto dto) {
        Page<UserVo> page = new Page<>(dto.getPage(),dto.getLimit());
        List<UserVo> userVos = baseMapper.queryUserVoByPage(page, dto.getRoleId(), dto.getOrgId(), dto.getKeyword());
        return page.setRecords(userVos);
    }

    @Override
    public void addOrUpdateUserByDto(UserDto dto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,dto.getUserName()).eq(User::getDelFlag,DelFlagEnum.NO_DEL.getDelFlag());
        List<User> users = baseMapper.selectList(queryWrapper);
        User user = dto.convertToUser(passwordEncoder);
        if(dto.getId()==null&& CollectionUtil.isNotEmpty(users)){
            //新增
            throw  new RuntimeException("用户名已存在");
        }else if(dto.getId()!=null&&CollectionUtil.isNotEmpty(users)&&user.getId()!=users.get(0).getId()){
            //新增
            throw  new RuntimeException("用户名已存在");
        }else{
            saveOrUpdate(user);
            //更新或修改user 对象及公司信息
            OrgInfo byId = orgInfoService.getById(dto.getOrgId());
            if(null==byId){
                throw  new RuntimeException("错误的公司Id");
            }
            //用户item扩展信息
            UserItem userItem = new UserItem();
            userItem.setUserId(user.getId());
            userItem.setOrgId(byId.getId());
            userItem.setOrgName(byId.getOrgName());
            userItem.setAddr(dto.getAddr());

            //用户角色关联信息
            RoleUser roleUser = new RoleUser();
            roleUser.setRoleId(dto.getRoleId());
            roleUser.setUserId(user.getId());

            if(dto.getId()==null){
                //新增角色和公司关系绑定
                iRoleUserService.save(roleUser);
                userItemService.save(userItem);
            }else{
                //更新角色和公司关系绑定
                iRoleUserService.updateByUserId(roleUser);
                userItemService.updateByUserId(userItem);
            }
        }

    }


    /**
     * 列表中获取用户信息
     *
     * @param users
     * @return
     */
    private User getUser(List<User> users) {
        User user = null;
        if (users != null && !users.isEmpty()) {
            user = users.get(0);
        }
        return user;
    }

    /*private Result checkEmailCode(UserDto dto){
        //@TODO 临时加通用验证码
        if(dto.getEmailCode().equals("00010")){
            return Result.succeed();
        }
        //校验邮箱验证码是否合法
        Object redisMailCode =
                redisRepository.get(String.format(RedisKeyEnum.MailRegisterKey.getKey(),dto.getEmail1()));
        log.info("query key :{}",String.format(RedisKeyEnum.MailRegisterKey.getKey(),dto.getEmail1()));
        if(redisMailCode==null){
            return Result.failed("验证码过期!");
        }
        if (StringUtils.isEmpty(redisMailCode) || !redisMailCode.toString().equalsIgnoreCase(dto.getEmailCode())) {
            return Result.failed("验证码错误");
        }
        return Result.succeed();
    }*/

    private User findByUserNameAndEmail(String userName, String email) {
        QueryWrapper query = new QueryWrapper();
        query.eq(CLOUMN_USERNAME, userName);
        query.eq(CLOUMN_EMAIL, email);
        return baseMapper.selectOne(query);
    }



    @Override
    public User findByUserId(Long userId) {
        return null;
    }

    @Override
    public List<Role> queryRoleByUserId(Long userId) {
        return null;
    }
}

