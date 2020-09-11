package com.imeng.web.vo;

import com.imeng.common.model.base.BaseVo;
import lombok.Data;

@Data
public class UserVo extends BaseVo {

    private String userName;
    private String nickName;
    private String telPhone;
    private boolean disable;
    private Long    orgId;
    private String  orgName;
    private String  addr;
    private Long    roleId;
    private String  roleName;

}
