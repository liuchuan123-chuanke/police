package com.imeng.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author zlt
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oauth_client_details")
public class Client implements Serializable {

    private static final long serialVersionUID = -8185413579135897885L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String clientId;

    /**
     * 应用名称
     */
    private String clientName;

    private String resourceIds = "";

    private String clientSecret;

    private String clientSecretStr;

    private String scope = "all";

    private String authorizedGrantTypes = "authorization_code,password,refresh_token,client_credentials";

    private String webServerRedirectUri;

    private String authorities = "";

    @TableField(value = "access_token_validity")
    private Integer accessTokenValiditySeconds = 18000;

    @TableField(value = "refresh_token_validity")
    private Integer refreshTokenValiditySeconds = 28800;

    private String additionalInformation = "{}";

    private String autoapprove = "true";

    private String secret;

    private String secretType;

}
