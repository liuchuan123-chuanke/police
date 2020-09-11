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
 * @date 2020-01-08 20:17:58
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_user")
public class RoleUser extends SuperEntity {
private static final long serialVersionUID=1L;

            private Long userId;
            private Long roleId;
                            }