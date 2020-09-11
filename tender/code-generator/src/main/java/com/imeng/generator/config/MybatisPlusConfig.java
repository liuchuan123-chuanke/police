package com.imeng.generator.config;

import com.imeng.db.config.DefaultMybatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zlt
 * @date 2018/12/10
 */
@Configuration
//@MapperScan({"com.imeng.generator.mapper.*"})
public class MybatisPlusConfig extends DefaultMybatisPlusConfig {

}
