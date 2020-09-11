package com.imeng.web.service;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;

/**
 * 继承ClientDetailsService,新加按client和scope的查询方法
 */
public interface ZltClientDetailsService extends ClientDetailsService {

    /**
     * 根据 两个参数查询ClientDetails
     * @param clientId
     * @param scope
     * @return
     */
    ClientDetails loadClientByKeyGen(String clientId, String scope);
}
