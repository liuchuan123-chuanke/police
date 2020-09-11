package com.imeng.web.mapper;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.model.User;
import com.imeng.db.mapper.SuperMapper;
import com.imeng.web.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 *
 * @author tw
 * @date 2020-01-07 15:30:46
 */
public interface UserMapper extends SuperMapper<User> {

    List<User> selectByUserParam(@Param("userName") String userName,
                                 @Param("phone")String phone, @Param("openId")String openId, @Param("userId")Long userId);

    /**
     * 分页查询用户vo
     * @param page
     * @param roleId
     * @param orgId
     * @param keyword
     * @return
     */
    List<UserVo> queryUserVoByPage(Page<UserVo> page,@Param("roleId") Long roleId,@Param("orgId")Long orgId,@Param("keyword")String keyword);
}

