package com.imeng.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.db.mapper.SuperMapper;
import com.imeng.web.model.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author tw
 * @date 2020-01-14 16:59:18
 */
public interface RoleMenuMapper extends SuperMapper<RoleMenu> {
    /**
     * 分页查询用户列表
     * @param page
     * @param params
     * @return
     */
    List<RoleMenu> findList(Page<RoleMenu> page, @Param("p") Map<String, Object> params);
}
