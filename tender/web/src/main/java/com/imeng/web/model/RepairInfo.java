package com.imeng.web.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.imeng.common.model.SuperEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 报修单信息表
 *
 * @author tw
 * @date 2020-09-10
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("sys_repair_info")
public class RepairInfo extends SuperEntity {
private static final long serialVersionUID=1L;

            private String repairNo;
            private Integer userId;
            private String userName;
            private Integer orgId;
            private String orgName;
            private String address;
            private String telphone;
            private Integer faultType;
            private String faultDesc;
            private String faultName;
            private Integer status;
            private Date subDate;
            private Integer optUserId;
            private Date warningDate;
            private String optUserName;
            private String tenantId;
                            }