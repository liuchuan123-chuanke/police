package com.imeng.web.vo;

import com.imeng.common.model.base.BaseVo;
import lombok.Data;

/**
 * @desc: 属性明细vo
 *
 * @author: jwy
 * @date: 2017/12/29
 */
@Data
public class AttributeDetailVo extends BaseVo {


    /**
     * 属性名
     */
    private String attrValue;
    /**
     * 属性名
     */
    private String attrName;

    /**
     * 属性排序
     */
    private Integer attrSort;

    /**
     * 属性说明
     */
    private String remarks;

}
