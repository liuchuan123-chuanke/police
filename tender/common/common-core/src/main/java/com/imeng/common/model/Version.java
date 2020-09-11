package com.imeng.common.model;

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
@TableName("sys_version")
public class Version extends SuperEntity {
private static final long serialVersionUID=1L;

            private String versionNo;
            private String updateReason;
            private String updateSql;
            private String warUrl;
                            }