package com.imeng.web.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.model.BlackIp;
import com.imeng.db.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author tw
 * @date 2020-02-19 10:41:45
 */
public interface BlackIpMapper extends SuperMapper<BlackIp> {
    /**
     * 分页查询用户列表
     * @param page
     * @param params
     * @return
     */
    List<BlackIp> findList(Page<BlackIp> page, @Param("p") Map<String, Object> params);
}
