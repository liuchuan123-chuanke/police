package com.imeng.web.service;

import com.imeng.common.model.BlackIp;
import com.imeng.common.model.PageResult;
import com.imeng.common.service.ISuperService;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author tw
 * @date 2020-02-19 10:41:45
 */
public interface IBlackIpService extends ISuperService<BlackIp> {
    /**
     * 列表
     * @param params
     * @return
     */
    PageResult<BlackIp> findList(Map<String, Object> params);

    /**
     * 查询黑名单列表
     * @param delFlag
     * @return
     */
    List<BlackIp> queryBlackIps(Integer delFlag, String tenantId);

    /**
     * 判断是否在和名单ip中
     * @param ip
     * @return
     */
    boolean isBlackIp(String ip);
}

