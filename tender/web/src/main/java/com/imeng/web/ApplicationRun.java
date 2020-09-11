package com.imeng.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * 程序的版本在启动类写死，避免修改数据库或配置导致版本不一致
 * @author wwj
 * 2020/6/2 14:38
 */
@SpringBootApplication
@ComponentScan("com.imeng")
@EnableCaching
public class ApplicationRun {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun.class, args);
    }

}
