package com.imeng.web.mapper;

import com.imeng.web.model.Equipment;
import com.imeng.db.mapper.SuperMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author tw
 * @date 2020-09-03
 */
public interface EquipmentMapper extends SuperMapper<Equipment> {
    /**
     * 分页查询用户列表
     * @param page
     * @param params
     * @return
     */
    List<Equipment> findList(Page<Equipment> page, @Param("p") Map<String, Object> params);
}
