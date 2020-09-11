package com.imeng.web.service;

import com.imeng.web.model.OrgInfo;
import com.imeng.common.model.PageResult;
import com.imeng.common.service.ISuperService;

import java.util.Map;

/**
 * 单位信息表
 *
 * @author tw
 * @date 2020-09-03
 */
public interface IOrgInfoService extends ISuperService<OrgInfo> {
    /**
     * 列表
     * @param params
     * @return
     */
    PageResult<OrgInfo> findList(Map<String, Object> params);
}

