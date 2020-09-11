package com.imeng.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.db.mapper.SuperMapper;
import com.imeng.web.model.AttributeDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author tw
 * @date 2020-02-20 16:43:51
 */
public interface AttributeDetailMapper extends SuperMapper<AttributeDetail> {
    /**
     * 分页查询用户列表
     * @param page
     * @param params
     * @return
     */
    List<AttributeDetail> findList(Page<AttributeDetail> page, @Param("p") Map<String, Object> params);
}
