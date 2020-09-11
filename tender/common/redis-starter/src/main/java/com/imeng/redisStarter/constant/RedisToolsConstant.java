package com.imeng.redisStarter.constant;

/**
 * @author wwj
 * 2020/6/2 12:05
 */
public class RedisToolsConstant {

    private RedisToolsConstant() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * single Redis
     */
    public final static int SINGLE = 1 ;

    /**
     * Redis cluster
     */
    public final static int CLUSTER = 2 ;
}
