package com.imeng.common.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wwj  删除表示枚举
 * 2020/1/10 10:58
 */

@AllArgsConstructor
@Getter
public enum DelFlagEnum {

    NO_DEL(0, "未删除"),
    DEL(1, "已删除");

    //删除标识
    private Integer delFlag;

    //删除说明
    private String desc;
}
