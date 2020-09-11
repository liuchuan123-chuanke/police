package com.imeng.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.model.Menu;
import com.imeng.db.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 *
 * @author tw
 * @date 2020-01-14 16:59:18
 */
public interface MenuMapper extends SuperMapper<Menu> {
    /**
     * 分页查询用户列表
     * @param page
     * @param params
     * @return
     */
    List<Menu> findList(Page<Menu> page, @Param("p") Map<String, Object> params);

    List<Menu> findMenusByRoleIds(@Param("roleIds") Set<Long> roleIds, @Param("type") Integer type,
                                  @Param("hidden") Integer hidden, @Param("delFlag") Integer delFlag);

    List<Menu> findMenusByRoleCodes(@Param("roleCodes") Set<String> roleCodes, @Param("type") Integer type, @Param("hidden") Integer hidden,
                                    @Param("delFlag") Integer delFlag);

    List<Menu> findByRoleId(@Param("roleId") Long roleId, @Param("type") Integer type,
                            @Param("hidden") Integer hidden, @Param("delFlag") Integer delFlag);
}
