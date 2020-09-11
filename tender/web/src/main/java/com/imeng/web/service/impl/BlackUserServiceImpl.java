package com.imeng.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imeng.common.Enum.DelFlagEnum;
import com.imeng.common.model.PageResult;
import com.imeng.common.service.impl.SuperServiceImpl;
import com.imeng.web.mapper.BlackUserMapper;
import com.imeng.web.model.BlackUser;
import com.imeng.web.service.IBlackUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author tw
 * @date 2020-02-19 10:41:45
 */
@Slf4j
@Service
public class BlackUserServiceImpl extends SuperServiceImpl<BlackUserMapper, BlackUser> implements IBlackUserService {

    private static final String CLOUMN_USERID = "user_id";

    /**
     * 列表
     * @param params
     * @return
     */
    @Override
    public PageResult<BlackUser> findList(Map<String, Object> params) {
        Page<BlackUser> page = new Page<>(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"));
        List<BlackUser> list = baseMapper.findList(page, params);
        return PageResult.<BlackUser>builder().data(list).code(0).count(page.getTotal()).build();
    }

    /**
     * 判断是否人员被拉入黑名单
     * @param userId
     * @return
     */
    @Override
    @Cacheable(value = "black:user")
    public boolean isBlackUser(Long userId) {
        QueryWrapper<BlackUser> query = new QueryWrapper<>();
        //默认没删除的状态
        query.eq(CLOUMN_DELFLG, DelFlagEnum.NO_DEL.getDelFlag());
        query.eq(CLOUMN_USERID,userId);
        return baseMapper.selectOne(query)!=null;
    }
}
