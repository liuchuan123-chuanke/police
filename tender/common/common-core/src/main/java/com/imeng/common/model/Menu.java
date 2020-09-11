package com.imeng.common.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

/**
 * @author tw
 * @date 2020-01-14 16:59:18
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("sys_menu")
public class Menu extends SuperEntity {

    private static final long serialVersionUID = 1L;

    private Long pId;

    private String pIds;

    private String name;

    private String pathName;

    private String url;

    private String icon;

    private Integer sort;

    private String permession;

    private Integer type;

    private Integer hidden;

    private String tenantId;

    @TableField(exist = false)
    private Long applicationId;

    //以下两个字段只有应用菜单时才有
    @TableField(exist = false)
    private Integer warning;     //警告等级

    @TableField(exist = false)
    private String warningDesc; //警告描述

    @TableField(exist = false)
    private List<Menu> subMenus;

    @TableField(exist = false)
    private Long roleId;

    @TableField(exist = false)
    private Set<Long> menuIds;

    /**
     * 是否选中  权限时使用
     */
    @TableField(exist = false)
    private boolean checked;
}