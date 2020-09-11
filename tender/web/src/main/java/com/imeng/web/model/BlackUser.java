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
 * @date 2020-02-19 10:41:45
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@TableName("sys_black_user")
public class BlackUser extends SuperEntity {
private static final long serialVersionUID=1L;

            private Integer userId;
            private String tenantId;
                            }