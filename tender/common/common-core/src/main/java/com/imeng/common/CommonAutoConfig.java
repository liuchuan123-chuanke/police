package com.imeng.common;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * 用作扫描feign
 *
 * @author wwj
 * 2020/1/16 19:43
 */
@EnableConfigurationProperties
@ComponentScan(basePackages = "com.central.common.feign")
public class CommonAutoConfig {

}
