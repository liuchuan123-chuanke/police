package com.imeng.web.vo;

import com.imeng.common.model.base.BaseVo;
import lombok.Data;

import java.util.List;

/**
 * @author wwj
 * 2020/2/20 17:31
 */
@Data
public class AttributeVo extends BaseVo {

    /**
     * 属性编码
     */
    private String attrCode;

    /**
     * 说明
     */
    private String name;

    /**
     * 备注说明
     */
    private String remarks;

    /**
     * 详细信息
     */
    private List<AttributeDetailVo> details;
}
