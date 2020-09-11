package com.imeng.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.model.BlackIp;
import com.imeng.common.model.PageResult;
import com.imeng.common.service.impl.SuperServiceImpl;
import com.imeng.web.mapper.BlackIpMapper;
import com.imeng.web.service.IBlackIpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author tw
 * @date 2020-02-19 10:41:45
 */
@Slf4j
@Service
public class BlackIpServiceImpl extends SuperServiceImpl<BlackIpMapper, BlackIp> implements IBlackIpService {
    /**
     * 列表
     * @param params
     * @return
     */
    @Override
    public PageResult<BlackIp> findList(Map<String, Object> params) {
        Page<BlackIp> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        List<BlackIp> list = baseMapper.findList(page, params);
        return PageResult.<BlackIp>builder().data(list).code(0).count(page.getTotal()).build();
    }

    /**
     * 查询黑名单列表
     * @param delFlag
     * @return
     */
    @Override
    @Cacheable(value = "black:ip",key = "#tenantId")
    public List<BlackIp> queryBlackIps(Integer delFlag,String tenantId) {
        QueryWrapper<BlackIp> query =new QueryWrapper();
        query.eq(CLOUMN_DELFLG,delFlag);
        if(!StringUtils.isEmpty(tenantId)){
            query.eq("tenant_id",tenantId);
        }
        return baseMapper.selectList(query);
    }

    @Override
    public boolean isBlackIp(String ip) {
        return false;
    }
}
