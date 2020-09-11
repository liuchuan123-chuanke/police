package com.imeng.web.service;

import com.imeng.web.model.RepairInfo;
import com.imeng.common.model.PageResult;
import com.imeng.common.service.ISuperService;

import java.util.Map;

/**
 * 报修单信息表
 *
 * @author tw
 * @date 2020-09-10
 */
public interface IRepairInfoService extends ISuperService<RepairInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    PageResult<RepairInfo> findList(Map<String, Object> params);
}

