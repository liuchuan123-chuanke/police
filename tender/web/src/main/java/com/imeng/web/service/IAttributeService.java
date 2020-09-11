package com.imeng.web.service;

import com.imeng.web.model.Attribute;
import com.imeng.common.model.PageResult;
import com.imeng.common.service.ISuperService;
import com.imeng.web.model.AttributeDetail;
import com.imeng.web.vo.AttributeVo;

import java.util.List;
import java.util.Map;

/**
 * 字典表
 *
 * @author tw
 * @date 2020-09-07
 */
public interface IAttributeService extends ISuperService<Attribute> {
    /**
     * 列表
     * @param params
     * @return
     */
    PageResult<Attribute> findList(Map<String, Object> params);

    /**
     *
     * @param attrCode
     * @param delFlag
     * @return
     */
    List<AttributeVo> findListByParam(String attrCode, Integer delFlag);

    void saveOrUpdateSafe(Attribute attribute) throws Exception;

    /**
     * 在某个字典码下添加详情
     * @param attrCode
     * @param detail
     */
    void addDetail(String attrCode,AttributeDetail detail);
}

