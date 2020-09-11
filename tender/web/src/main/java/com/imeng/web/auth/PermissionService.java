package com.imeng.web.auth;

import cn.hutool.core.collection.CollectionUtil;
import com.imeng.auth.properties.SecurityProperties;
import com.imeng.auth.service.impl.DefaultPermissionServiceImpl;
import com.imeng.auth.util.AuthUtils;
import com.imeng.common.Enum.DelFlagEnum;
import com.imeng.common.Enum.MenuShowEnum;
import com.imeng.common.Enum.MenuTypeEnum;
import com.imeng.common.constant.CommonConstant;
import com.imeng.common.context.TenantContextHolder;
import com.imeng.common.model.BlackIp;
import com.imeng.common.model.Menu;
import com.imeng.common.model.SimpleClientDto;
import com.imeng.common.model.SuperEntity;
import com.imeng.common.utils.AddrUtil;
import com.imeng.web.service.IBlackIpService;
import com.imeng.web.service.IMenuService;
import com.imeng.web.util.ReactiveAddrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wwj
 * 2020/6/4 14:28
 */
@Slf4j
@Service("permissionService")
public class PermissionService extends DefaultPermissionServiceImpl {

    @Resource
    private IMenuService menuService;
    @Autowired
    private SecurityProperties securityProperties;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Autowired
    private IBlackIpService blackIpService;


    @Override
    public List<Menu> findMenuByRoleCodes(String roleCodes) {
        Set<String> set = new HashSet<>();
        set.add(roleCodes);

        return menuService.findMenusByRoleCodes(set, MenuTypeEnum.API.getType(), MenuShowEnum.DISPLAY.getType(),
                DelFlagEnum.NO_DEL.getDelFlag());
    }

    private List<Menu> findMenuByRoleCodesSet(Set<String> set) {

        return menuService.findMenusByRoleCodes(set, MenuTypeEnum.API.getType(), MenuShowEnum.DISPLAY.getType(),
                DelFlagEnum.NO_DEL.getDelFlag());
    }
    private boolean isBlackIp(HttpServletRequest httpServletRequest, List<BlackIp> list){
        String ip = AddrUtil.getRemoteAddr(httpServletRequest);
        if(CollectionUtil.isNotEmpty(list)) {
            for (BlackIp blackIp : list) {
                if (ReactiveAddrUtil.isBetweenIps(ip, blackIp.getIpStart(), blackIp.getIpEnd())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            OAuth2Authentication oauth2Authentication = (OAuth2Authentication) authentication;
            String clientId = oauth2Authentication.getOAuth2Request().getClientId();
            List<BlackIp> list = blackIpService.queryBlackIps(DelFlagEnum.NO_DEL.getDelFlag(), clientId);
            if (isBlackIp(request, list)) {
                throw new OAuth2Exception("非法Ip");
            }
        }
        return hasPermission(authentication, request.getMethod(), request.getRequestURI());
    }

    @Override
    public boolean hasPermission(Authentication authentication, String requestMethod, String requestURI){
        // 前端跨域OPTIONS请求预检放行 也可通过前端配置代理实现
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(requestMethod)) {
            return true;
        }
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            //判断是否开启url权限验证
            if (!securityProperties.getAuth().getUrlPermission().getEnable()) {
                return true;
            }
            //role code 为admin 放行
            List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
            if (CollectionUtil.isEmpty(grantedAuthorityList)) {
                log.warn("角色列表为空：{}", authentication.getPrincipal());
                return false;
            }
            Set<String> collect =
                    grantedAuthorityList.parallelStream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toSet());
            if (collect.contains(CommonConstant.ADMIN_USER_NAME)) {
                //管理员所有访问
                return true;
            }

            OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
            //判断不进行url权限认证的api，所有已登录用户都能访问的url
            for (String path : securityProperties.getAuth().getUrlPermission().getIgnoreUrls()) {
                if (antPathMatcher.match(path, requestURI)) {
                    return true;
                }
            }
            //保存租户信息
            String clientId = auth2Authentication.getOAuth2Request().getClientId();
            String scope = auth2Authentication.getOAuth2Request().getScope().iterator().next();
            TenantContextHolder.setTenant(new SimpleClientDto(clientId,null,
                    scope));

           /* //String roleCodes =
                    grantedAuthorityList.stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.joining(", "));*/
            List<Menu> menuList = findMenuByRoleCodesSet(collect);
            for (Menu menu : menuList) {
                if (StringUtils.isNotEmpty(menu.getUrl()) && antPathMatcher.match(menu.getUrl(), requestURI)) {
                    return true;
                }
            }
        }

        return false;
    }
}
