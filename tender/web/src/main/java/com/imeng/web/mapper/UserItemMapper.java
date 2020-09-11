package com.imeng.web.mapper;

import com.imeng.web.model.UserItem;
import com.imeng.db.mapper.SuperMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户item表 作为用户拓展字段
 *
 * @author tw
 * @date 2020-09-04
 */
public interface UserItemMapper extends SuperMapper<UserItem> {
    /**
     * 分页查询用户列表
     * @param page
     * @param params
     * @return
     */
    List<UserItem> findList(Page<UserItem> page, @Param("p") Map<String, Object> params);
}
