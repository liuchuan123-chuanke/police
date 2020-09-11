/*
package com.imeng.web.auth;

import cn.hutool.core.collection.CollectionUtil;
import com.imeng.auth.service.impl.DefaultPermissionServiceImpl;
import com.imeng.common.Enum.DelFlagEnum;
import com.imeng.common.Enum.MenuTypeEnum;
import com.imeng.common.context.TenantContextHolder;
import com.imeng.common.context.UserContextHolder;
import com.imeng.common.model.*;
import com.imeng.common.utils.AddrUtil;
import com.imeng.common.utils.ResponseUtil;
import com.imeng.web.framework.Enum.MenuShowEnum;
import com.imeng.web.service.IBlackIpService;
import com.imeng.web.service.IMenuService;
import com.imeng.web.util.ReactiveAddrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

*/
/**
 * url权限认证
 *
 * @author zlt
 * @date 2019/10/6
 * <p>
 * Blog: https://blog.csdn.net/zlt2000
 * Github: https://github.com/zlt2000
 *//*

@Slf4j
@Component
public class PermissionAuthManager extends DefaultPermissionServiceImpl implements AccessDecisionManager {
    @Resource
    private IMenuService menuService;

    @Autowired
    private IBlackIpService blackIpService;
    @Override
    public List<Menu> findMenuByRoleCodes(String roleCodes)
    {
        Set<String> set = new HashSet<>();
        set.add(roleCodes);

        return menuService.findMenusByRoleCodes(set, MenuTypeEnum.API.getType(), MenuShowEnum.DISPLAY.getType(),
                DelFlagEnum.NO_DEL.getDelFlag());
    }
    */
/**
     * 判断Ip是否在黑名单当中
     * @param httpServletRequest
     * @param list
     * @return
     *//*

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


    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        boolean isPermission =
        super.hasPermission(authentication, ((FilterInvocation) o).getHttpRequest().getMethod(),
                ((FilterInvocation) o).getRequestUrl());
        if(!isPermission){
            throw new AccessDeniedException("权限不足!");
        }
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
*/
