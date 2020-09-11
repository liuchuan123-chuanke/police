package com.imeng.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wwj
 * 2020/1/15 15:41
 */
@Data
@ApiModel(value = "授权包装类")
public class OauthDto {

    @ApiModelProperty(name = "登录名")
    private String userName;
    @ApiModelProperty(name = "密码")
    private String password;
    @ApiModelProperty(name = "openId")
    private String openId;
    @ApiModelProperty(name = "手机号")
    private String phone;

}
