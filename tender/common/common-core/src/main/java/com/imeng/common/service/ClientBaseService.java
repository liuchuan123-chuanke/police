package com.imeng.common.service;

import com.imeng.common.model.Client;

/**
 * @author wwj
 * 2020/6/2 14:24
 */
public interface ClientBaseService {

    Client queryByClientId(String clientId);
}
