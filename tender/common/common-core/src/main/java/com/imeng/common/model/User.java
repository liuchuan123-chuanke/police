package com.imeng.common.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.util.List;

/**
 * @author tw
 * @date 2020-01-07 15:30:46
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User extends SuperEntity {

    private static final long serialVersionUID = 1L;

    private String userName;

    private String nickName;

    private String pwd;

    private String phone1;


    private String headImg;

    private String gender;


    private String openId;

    private String tenantId;

    private boolean disable;

    @TableField(exist = false)
    private List<Role> roles;

    @TableField(exist = false)
    private Long roleId;
    @TableField(exist = false)
    private String roleCode;





    public int getApplicationPermissionLevel() {
        int level = 0;
        if(roles==null){
            return 0;
        }
        for (Role role : roles) {
            if (role.isSuperAdmin()) {
                level = 1;
            } else if (role.isCompanyAdmin()) {
                level = 2;
            } else {
                level = 0;
            }
        }
        return level;
    }

    /**
     * 判断是否是超级管理员
     * 超级管理员角色编码为 admin
     *
     * @return
     */
    public boolean isAdmin() {
        boolean flag = false;
        //判断是否是超级管理员
        if (roles != null) {
            for (int i = 0; i < roles.size(); i++) {
                if (roles.get(i).getCode().equalsIgnoreCase("admin")) {
                    flag = true;
                }
            }
        }
        return flag;
    }


}
