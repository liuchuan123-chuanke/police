package com.imeng.common.resolver;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.imeng.common.annotation.LoginUser;
import com.imeng.common.constant.SecurityConstants;
import com.imeng.common.context.UserContextHolder;
import com.imeng.common.model.Role;
import com.imeng.common.model.SimpleUserDto;
import com.imeng.common.model.User;
import com.imeng.common.service.UserBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Token转化User
 *
 * @author zlt
 * @date 2018/12/21
 */
@Slf4j
public class TokenArgumentResolver implements HandlerMethodArgumentResolver {

    private UserBaseService userBaseService;

    public TokenArgumentResolver(UserBaseService userService) {
        this.userBaseService = userService;
    }

    /**
     * 入参筛选
     *
     * @param methodParameter 参数集合
     * @return 格式化后的参数
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(LoginUser.class) && methodParameter.getParameterType().equals(User.class);
    }

    /**
     * @param methodParameter       入参集合
     * @param modelAndViewContainer model 和 view
     * @param nativeWebRequest      web相关
     * @param webDataBinderFactory  入参解析
     * @return 包装对象
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {
        log.info("===============================用户参数解析=======================");
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.err.println(JSON.toJSONString("授权信息:"+authentication));
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            //客户端模式只返回一个clientId
            if (principal instanceof User) {
                user = (User)authentication.getPrincipal();
                System.err.println(JSON.toJSONString("授权信息:"+user));
            }
            OAuth2Authentication oauth2Authentication = (OAuth2Authentication)authentication;
            String clientId = oauth2Authentication.getOAuth2Request().getClientId();
            log.info("clientId:{}",clientId);
        }
        LoginUser loginUser = methodParameter.getParameterAnnotation(LoginUser.class);

        boolean isFull = loginUser.isFull();
        if(user!=null&&isFull) {
            List<Role> sysRoleList = userBaseService.queryRoleByUserId(user.getId());
            user.setRoles(sysRoleList);
        }
        //UserContextHolder.setUser(new SimpleUserDto(userId,username,statusStr,fullName));
        return user;
    }
}
