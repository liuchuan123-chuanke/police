package com.imeng.web.service;

import com.imeng.common.model.PageResult;
import com.imeng.common.service.ISuperService;
import com.imeng.web.model.AttributeDetail;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author tw
 * @date 2020-02-20 16:43:51
 */
public interface IAttributeDetailService extends ISuperService<AttributeDetail> {
    /**
     * 列表
     * @param params
     * @return
     */
    PageResult<AttributeDetail> findList(Map<String, Object> params);


    List<AttributeDetail> queryByParam(Long attrId);

    void deleteByAttrId(Long attrId);
}
