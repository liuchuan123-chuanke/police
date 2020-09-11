package com.imeng.common.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tw
 * @date 2020-01-14 16:59:18
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class Role extends SuperEntity {

    private static final long serialVersionUID = 1L;

    private String code;

    private String name;

    private Integer status;

    private String tenantId;

    public boolean isSuperAdmin() {
        return "admin".equals(code);
    }

    public boolean isCompanyAdmin() {
        return "companyAdmin".equals(code);
    }
}