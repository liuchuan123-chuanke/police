package com.imeng.common.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tw
 * @date 2020-02-19 10:41:45
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("sys_black_ip")
public class BlackIp extends SuperEntity {

    private static final long serialVersionUID = 1L;

    private String ipStart;

    private String ipEnd;

    private Integer authType;

    private String note;

    private String tenantId;
}