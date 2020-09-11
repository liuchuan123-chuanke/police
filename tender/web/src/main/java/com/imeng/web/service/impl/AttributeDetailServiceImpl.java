package com.imeng.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.Enum.DelFlagEnum;
import com.imeng.common.model.PageResult;
import com.imeng.common.service.impl.SuperServiceImpl;
import com.imeng.web.mapper.AttributeDetailMapper;
import com.imeng.web.model.AttributeDetail;
import com.imeng.web.service.IAttributeDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author tw
 * @date 2020-02-20 16:43:51
 */
@Slf4j
@Service
public class AttributeDetailServiceImpl extends SuperServiceImpl<AttributeDetailMapper, AttributeDetail> implements IAttributeDetailService {

    private static final String CLOUMN_ATTRID = "attr_id";

    /**
     * 列表
     * @param params
     * @return
     */
    @Override
    public PageResult<AttributeDetail> findList(Map<String, Object> params) {
        Page<AttributeDetail> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        List<AttributeDetail> list = baseMapper.findList(page, params);
        return PageResult.<AttributeDetail>builder().data(list).code(0).count(page.getTotal()).build();
    }

    /**
     * 按attrId查询列表
     * @param attrId
     * @return
     */
    @Override
    public List<AttributeDetail> queryByParam(Long attrId) {
        QueryWrapper query = new QueryWrapper();
        query.eq(CLOUMN_ATTRID,attrId);
        return baseMapper.selectList(query);
    }

    @Override
    public void deleteByAttrId(Long attrId) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set(CLOUMN_DELFLG, DelFlagEnum.DEL.getDelFlag());
        updateWrapper.eq(CLOUMN_ATTRID,attrId);
        update(updateWrapper);
    }
}
