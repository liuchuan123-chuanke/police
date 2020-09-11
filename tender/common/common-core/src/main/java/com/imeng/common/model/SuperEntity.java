package com.imeng.common.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体父类
 *
 * @author zlt
 */
@Setter
@Getter
public class SuperEntity<T extends Model<?>> extends Model<T> {

    /**
     * 创建者
     */

    @TableField(value = "create_by", fill = FieldFill.INSERT)
    protected Long createId;

    /**
     * 创建日期
     */

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    protected Date createDate;

    /**
     * 更新者
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    protected Long updateId;

    /**
     * 更新日期
     */
    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    protected Date updateDate;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    protected String remark;

    /**
     * 删除标记（0：正常；1：删除）
     */
    @TableField(value = "del_flag")
    protected Integer delFlag;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
