package com.imeng.web.dto;

import com.imeng.common.model.base.BaseDto;
import lombok.Data;

/**
 * @author wwj
 * 2020/4/10 15:45
 */
@Data
public class RoleMenuDto extends BaseDto {

    Long roleId;
    Long[] menuIds;

}
