package com.imeng.web.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.imeng.common.model.SuperEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
    
/**
 * 
 *
 * @author tw
 * @date 2020-09-03
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("sys_equipment")
public class Equipment extends SuperEntity {
private static final long serialVersionUID=1L;

            private String equipmentName;
            private Integer orgId;
            private String orgName;
            private String tenantId;
    }