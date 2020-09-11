package com.imeng.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imeng.common.Enum.DelFlagEnum;
import com.imeng.common.lock.DistributedLock;
import com.imeng.web.Enum.RedisLockKeyEnum;
import com.imeng.web.model.AttributeDetail;
import com.imeng.web.service.IAttributeDetailService;
import com.imeng.web.vo.AttributeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.imeng.common.model.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.service.impl.SuperServiceImpl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import lombok.extern.slf4j.Slf4j;

import com.imeng.web.model.Attribute;
import com.imeng.web.mapper.AttributeMapper;
import com.imeng.web.service.IAttributeService;

/**
 * 字典表
 *
 * @author tw
 * @date 2020-09-07
 */
@Slf4j
@Service
public class AttributeServiceImpl extends SuperServiceImpl<AttributeMapper, Attribute> implements IAttributeService {

    @Autowired
    DistributedLock distributedLock;
    @Autowired
    IAttributeDetailService detailService;
    /**
     * 列表
     * @param params
     * @return
     */
    @Override
    public PageResult<Attribute> findList(Map<String, Object> params) {
        Page<Attribute> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        List<Attribute> list = baseMapper.findList(page, params);
        return PageResult.<Attribute>builder().data(list).code(0).count(page.getTotal()).build();
    }
    /**
     *
     * @param attrCode
     * @param delFlag
     * @return
     */
    @Override
    public List<AttributeVo> findListByParam(String attrCode, Integer delFlag) {
        return baseMapper.queryAttributeVo(attrCode,delFlag);
    }

    /**
     * 线程安全的保存和更新
     * @param attribute
     */
    @Override
    public void saveOrUpdateSafe(Attribute attribute) throws Exception {
        String lockKey = String.format(RedisLockKeyEnum.ATTR_SAVE_UPDATE.getLockKey(),attribute.getAttrCode());
        LambdaQueryWrapper<Attribute> query = new LambdaQueryWrapper<>();
        query.eq(Attribute::getAttrCode,attribute.getAttrCode());
        saveOrUpdateIdempotency(attribute,distributedLock,lockKey,query,"attrCode 不能重复");
    }

    /**
     * 添加
     * @param attrCode
     * @param detail
     */
    @Override
    public void addDetail(String attrCode, AttributeDetail detail) {
        List<AttributeVo> attributeVos = baseMapper.queryAttributeVo(attrCode, DelFlagEnum.NO_DEL.getDelFlag());
        if(CollectionUtil.isNotEmpty(attributeVos)){
            AttributeVo attributeVo = attributeVos.get(0);
            detail.setAttrId(attributeVo.getId().intValue());
            detailService.save(detail);
        }
    }
}
