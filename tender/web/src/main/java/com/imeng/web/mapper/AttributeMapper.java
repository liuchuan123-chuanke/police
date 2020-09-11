package com.imeng.web.mapper;

import com.imeng.web.model.Attribute;
import com.imeng.db.mapper.SuperMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.web.vo.AttributeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 字典表
 *
 * @author tw
 * @date 2020-09-07
 */
public interface AttributeMapper extends SuperMapper<Attribute> {
    /**
     * 分页查询用户列表
     * @param page
     * @param params
     * @return
     */
    List<Attribute> findList(Page<Attribute> page, @Param("p") Map<String, Object> params);

    /**
     * 按id查询详细
     * @param attrCode
     * @return
     */
    List<AttributeVo> queryAttributeVo(@Param("attrCode") String attrCode, @Param("delFlag")Integer delFlag);
}
