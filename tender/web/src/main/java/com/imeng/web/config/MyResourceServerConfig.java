package com.imeng.web.config;

import com.imeng.auth.config.DefaultResourceServerConf;
import com.imeng.common.config.DefaultPasswordConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author wwj
 * 2020/6/4 14:05
 */
@Configuration
@EnableResourceServer
@Import({DefaultPasswordConfig.class})
public class MyResourceServerConfig extends DefaultResourceServerConf {

    @Override
    public HttpSecurity setAuthenticate(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl) {
        return super.setAuthenticate(authorizedUrl);
    }
}
