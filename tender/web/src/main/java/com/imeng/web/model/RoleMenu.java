package com.imeng.web.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.imeng.common.model.SuperEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 *
 * @author tw
 * @date 2020-01-14 16:59:18
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_menu")
@NoArgsConstructor
public class RoleMenu extends SuperEntity {
private static final long serialVersionUID=1L;

            private Long roleId;
            private Long menuId;


    public RoleMenu(Long roleId, Long menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }
}