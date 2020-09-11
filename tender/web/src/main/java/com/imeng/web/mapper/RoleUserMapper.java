package com.imeng.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.db.mapper.SuperMapper;
import com.imeng.web.model.RoleUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author tw
 * @date 2020-01-08 20:17:58
 */
public interface RoleUserMapper extends SuperMapper<RoleUser> {
    /**
     * 分页查询用户列表
     * @param page
     * @param params
     * @return
     */
    List<RoleUser> findList(Page<RoleUser> page, @Param("p") Map<String, Object> params);
}
