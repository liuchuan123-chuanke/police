package com.imeng.common.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wwj
 * 2020/1/19 17:18
 */
@AllArgsConstructor
@Getter
public enum MenuShowEnum {

    HIDDEN(1,"隐藏"),
    DISPLAY(0,"显示");

    private int      type;
    private String   desc;
}
