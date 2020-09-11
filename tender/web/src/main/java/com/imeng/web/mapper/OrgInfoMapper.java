package com.imeng.web.mapper;

import com.imeng.web.model.OrgInfo;
import com.imeng.db.mapper.SuperMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 单位信息表
 *
 * @author tw
 * @date 2020-09-03
 */
public interface OrgInfoMapper extends SuperMapper<OrgInfo> {
    /**
     * 分页查询用户列表
     * @param page
     * @param params
     * @return
     */
    List<OrgInfo> findList(Page<OrgInfo> page, @Param("p") Map<String, Object> params);
}
