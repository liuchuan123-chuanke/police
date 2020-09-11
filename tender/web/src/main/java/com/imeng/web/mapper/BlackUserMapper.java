package com.imeng.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.db.mapper.SuperMapper;
import com.imeng.web.model.BlackUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author tw
 * @date 2020-02-19 10:41:45
 */
public interface BlackUserMapper extends SuperMapper<BlackUser> {
    /**
     * 分页查询用户列表
     * @param page
     * @param params
     * @return
     */
    List<BlackUser> findList(Page<BlackUser> page, @Param("p") Map<String, Object> params);
}
