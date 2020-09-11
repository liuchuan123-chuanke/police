package com.imeng.common.model.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wwj
 * 2020/1/9 20:24
 */
@Data
public class BaseVo implements Serializable {

    //主键id
    private Long id;

    //创建者id
    private Long createBy;

    //创建时间
    private Date createTime;

    //状态
    private Integer status;

    //类型
    private Integer type;

    //状态描述
    private String statusDesc;

}
