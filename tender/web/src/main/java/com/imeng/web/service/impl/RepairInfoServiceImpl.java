package com.imeng.web.service.impl;

import org.springframework.stereotype.Service;
import com.imeng.common.model.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.service.impl.SuperServiceImpl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;

import com.imeng.web.model.RepairInfo;
import com.imeng.web.mapper.RepairInfoMapper;
import com.imeng.web.service.IRepairInfoService;

/**
 * 报修单信息表
 *
 * @author tw
 * @date 2020-09-10
 */
@Slf4j
@Service
public class RepairInfoServiceImpl extends SuperServiceImpl<RepairInfoMapper, RepairInfo> implements IRepairInfoService {
    /**
     * 列表
     * @param params
     * @return
     */
    @Override
    public PageResult<RepairInfo> findList(Map<String, Object> params) {
        Page<RepairInfo> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        List<RepairInfo> list = baseMapper.findList(page, params);
        return PageResult.<RepairInfo>builder().data(list).code(0).count(page.getTotal()).build();
    }
}
