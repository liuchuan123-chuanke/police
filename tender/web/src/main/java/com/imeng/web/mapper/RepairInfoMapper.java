package com.imeng.web.mapper;

import com.imeng.web.model.RepairInfo;
import com.imeng.db.mapper.SuperMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 报修单信息表
 *
 * @author tw
 * @date 2020-09-10
 */
public interface RepairInfoMapper extends SuperMapper<RepairInfo> {
    /**
     * 分页查询用户列表
     * @param page
     * @param params
     * @return
     */
    List<RepairInfo> findList(Page<RepairInfo> page, @Param("p") Map<String, Object> params);
}
