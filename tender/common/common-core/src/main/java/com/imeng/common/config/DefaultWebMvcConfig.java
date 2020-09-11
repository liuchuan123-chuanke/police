package com.imeng.common.config;

import com.imeng.common.resolver.ClientArgumentResolver;
import com.imeng.common.resolver.TokenArgumentResolver;
import com.imeng.common.service.ClientBaseService;
import com.imeng.common.service.UserBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

/**
 * 默认SpringMVC拦截器
 *
 * @author zlt
 * @date 2019/8/5
 */
@Slf4j
public class DefaultWebMvcConfig extends WebMvcConfigurationSupport {

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
