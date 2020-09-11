package com.imeng.web.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * 请求地址配置类
 * @author wwj
 * 2020/6/11 14:46
 */

@Component
@ConfigurationProperties("hm.request.url")
@Data
public class RequestAppUrlProperties {

    /**
     * 业务上传
     */
    private String bizUpUrl;

    /**
     * 业务证据查询
     */
    private String bizQueryUrl;

    /**
     * 合同信息上传
     */
    private String contractUpUrl;

    /**
     * 合同查询url
     */
    private String contractQueryUrl;

    /**
     * 合同批量查询接口
     */
    private String contractBatchQueryUrl;

    /**
     * 业务证据批量查询接口
     */
    private String bizBatchQueryUrl;

    /**
     * 统计信息查询
     */
    private String bizBlockCountUrl;

    /**
     * pdf 签章信息读取接口
     */
    private String bizPdfSignCheckUrl;

    /**
     * 基础url
     */
    private String baseUrl;

}
