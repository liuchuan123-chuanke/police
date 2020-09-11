package com.imeng.web.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.imeng.common.model.SuperEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
    
/**
 * 用户item表 作为用户拓展字段
 *
 * @author tw
 * @date 2020-09-04
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_item")
public class UserItem extends SuperEntity {
private static final long serialVersionUID=1L;

            private Long orgId;
            private String orgName;
            private String addr;
                                    private String tenantId;
            private Long userId;
    }