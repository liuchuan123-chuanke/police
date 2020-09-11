package com.imeng.web.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.imeng.common.model.SuperEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 *
 *
 * @author tw
 * @date 2020-02-20 16:43:51
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("sys_attribute_detail")
public class AttributeDetail extends SuperEntity {
    private static final long serialVersionUID=1L;

    private Integer attrId;
    private String attrName;
    private String attrValue;
    private String tenantId;
    private Integer attrSort;
}
