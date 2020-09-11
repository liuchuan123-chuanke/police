package com.imeng.web.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wwj
 * 2020/6/8 09:44
 */
@Component
@ConfigurationProperties("hm.app")
@Data
public class RequestAppProperties {

    //appKey
    private String  appKey;
    //密钥
    private String  secret;
}
