package com.imeng.common.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wwj
 * 2020/2/25 18:45
 */
@AllArgsConstructor
@Getter
public enum MenuTypeEnum {

    MENU(1,"菜单"),
    API(2,"HTTP接口"),
    //@TODO webSocket考虑不全 后面添加
    SOCKET_API(3,"socket接口");
    private Integer type; //类型
    private String  typeDesc;//
}
