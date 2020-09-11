package com.imeng.web.Enum;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wwj
 * 2020/1/20 17:17
 */
@AllArgsConstructor
@Getter
public enum RedisLockKeyEnum {

    ATTR_SAVE_UPDATE("lock_attr_saveOrUpdate:%s","字典的保存和更新");
    //锁名
    private String lockKey;
    //锁说明 描述
    private String keyDesc;
}
