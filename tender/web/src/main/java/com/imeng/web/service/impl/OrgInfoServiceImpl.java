package com.imeng.web.service.impl;

import org.springframework.stereotype.Service;
import com.imeng.common.model.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.service.impl.SuperServiceImpl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;

import com.imeng.web.model.OrgInfo;
import com.imeng.web.mapper.OrgInfoMapper;
import com.imeng.web.service.IOrgInfoService;

/**
 * 单位信息表
 *
 * @author tw
 * @date 2020-09-03
 */
@Slf4j
@Service
public class OrgInfoServiceImpl extends SuperServiceImpl<OrgInfoMapper, OrgInfo> implements IOrgInfoService {
    /**
     * 列表
     * @param params
     * @return
     */
    @Override
    public PageResult<OrgInfo> findList(Map<String, Object> params) {
        Page<OrgInfo> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        List<OrgInfo> list = baseMapper.findList(page, params);
        return PageResult.<OrgInfo>builder().data(list).code(0).count(page.getTotal()).build();
    }
}
