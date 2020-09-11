package com.imeng.web.config;

import com.imeng.db.config.DefaultMybatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zlt
 * @date 2018/12/10
 */
@Configuration
@MapperScan({"com.imeng.web.mapper*"})
public class MybatisPlusConfig extends DefaultMybatisPlusConfig {

}
