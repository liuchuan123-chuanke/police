package com.imeng.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.model.Role;
import com.imeng.db.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author tw
 * @date 2020-01-14 16:59:18
 */
public interface RoleMapper extends SuperMapper<Role> {
    /**
     * 分页查询用户列表
     * @param page
     * @param params
     * @return
     */
    List<Role> findList(Page<Role> page, @Param("p") Map<String, Object> params);

    List<Role> findRolesByUserId(Long userId);
}
