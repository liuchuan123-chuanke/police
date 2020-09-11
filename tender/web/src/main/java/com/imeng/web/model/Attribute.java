package com.imeng.web.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.imeng.common.model.SuperEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
    
/**
 * 字典表
 *
 * @author tw
 * @date 2020-09-07
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("sys_attribute")
public class Attribute extends SuperEntity {
private static final long serialVersionUID=1L;

            private String attrCode;
            private String name;
            private String tenantId;
                            }