package com.imeng.web.service;

import com.imeng.web.model.Equipment;
import com.imeng.common.model.PageResult;
import com.imeng.common.service.ISuperService;

import java.util.Map;

/**
 * 
 *
 * @author tw
 * @date 2020-09-03
 */
public interface IEquipmentService extends ISuperService<Equipment> {
    /**
     * 列表
     * @param params
     * @return
     */
    PageResult<Equipment> findList(Map<String, Object> params);
}

