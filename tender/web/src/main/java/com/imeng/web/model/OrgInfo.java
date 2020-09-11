package com.imeng.web.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.imeng.common.model.SuperEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
    
/**
 * 单位信息表
 *
 * @author tw
 * @date 2020-09-03
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("sys_org_info")
public class OrgInfo extends SuperEntity {
private static final long serialVersionUID=1L;

            private String orgName;
            private String orgAddr;
            private String tenantId;
                            }