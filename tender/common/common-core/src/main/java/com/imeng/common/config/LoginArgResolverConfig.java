package com.imeng.common.config;

import com.imeng.common.resolver.ClientArgumentResolver;
import com.imeng.common.resolver.TokenArgumentResolver;
import com.imeng.common.service.ClientBaseService;
import com.imeng.common.service.UserBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 公共配置类, 一些公共工具配置
 *
 * @author zlt
 * @date 2018/8/25
 */
public class LoginArgResolverConfig implements WebMvcConfigurer {

    @Lazy
    @Autowired
    private UserBaseService userBaseService;

    @Lazy
    @Autowired
    private ClientBaseService clientBaseService;

    /**
     * Token参数解析
     *
     * @param argumentResolvers 解析类
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //注入用户信息
        argumentResolvers.add(new TokenArgumentResolver(userBaseService));
        //注入应用信息
        argumentResolvers.add(new ClientArgumentResolver(clientBaseService));
    }
}
