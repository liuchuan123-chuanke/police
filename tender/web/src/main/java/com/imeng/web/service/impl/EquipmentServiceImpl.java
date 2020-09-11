package com.imeng.web.service.impl;

import org.springframework.stereotype.Service;
import com.imeng.common.model.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.service.impl.SuperServiceImpl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;

import com.imeng.web.model.Equipment;
import com.imeng.web.mapper.EquipmentMapper;
import com.imeng.web.service.IEquipmentService;

/**
 * 
 *
 * @author tw
 * @date 2020-09-03
 */
@Slf4j
@Service
public class EquipmentServiceImpl extends SuperServiceImpl<EquipmentMapper, Equipment> implements IEquipmentService {
    /**
     * 列表
     * @param params
     * @return
     */
    @Override
    public PageResult<Equipment> findList(Map<String, Object> params) {
        Page<Equipment> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        List<Equipment> list = baseMapper.findList(page, params);
        return PageResult.<Equipment>builder().data(list).code(0).count(page.getTotal()).build();
    }
}
