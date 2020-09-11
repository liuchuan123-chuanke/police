package com.imeng.common.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imeng.common.exception.IdempotencyException;
import com.imeng.common.exception.LockException;
import com.imeng.common.lock.DistributedLock;
import com.imeng.common.service.ISuperService;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * service实现父类
 *
 * @author zlt
 * @date 2019/1/10
 */
public class SuperServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements ISuperService<T> {

    public static final String CLOUMN_DELFLG = "del_flag";

    public static final String CLOUMN_CREATEBY = "create_by";

    public static final String CLOUMN_CREATEDATE = "create_date";

    public static final String CLOUMN_UPDATEBY = "update_by";

    public static final String CLOUMN_UPDATEDATE = "update_date";

    public static final String CLOUMN_REMARKS = "remarks";

    @Override
    public boolean saveIdempotency(T entity, DistributedLock locker, String lockKey, Wrapper<T> countWrapper, String msg) throws Exception {
        if (locker == null) {
            throw new LockException("DistributedLock is null");
        }
        if (StrUtil.isEmpty(lockKey)) {
            throw new LockException("lockKey is null");
        }
        Object lock = null;
        try {
            //加锁
            lock = locker.tryLock(lockKey, 10, 60, TimeUnit.SECONDS);
            if (lock != null) {
                //判断记录是否已存在
                int count = super.count(countWrapper);
                if (count == 0) {
                    return super.save(entity);
                } else {
                    if (StrUtil.isEmpty(msg)) {
                        msg = "已存在";
                    }
                    throw new IdempotencyException(msg);
                }
            } else {
                throw new LockException("锁等待超时");
            }
        } finally {
            locker.unlock(lock);
        }
    }

    @Override
    public boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper) throws Exception {
        return saveIdempotency(entity, lock, lockKey, countWrapper, null);
    }

    @Override
    public boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg) throws Exception {
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            if (null != tableInfo && StrUtil.isNotEmpty(tableInfo.getKeyProperty())) {
                Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
                if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
                    if (StrUtil.isEmpty(msg)) {
                        msg = "已存在";
                    }
                    return this.saveIdempotency(entity, lock, lockKey, countWrapper, msg);
                } else {
                    return updateById(entity);
                }
            } else {
                throw ExceptionUtils.mpe("Error:  Can not execute. Could not find @TableId.");
            }
        }
        return false;
    }

    @Override
    public boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper) throws Exception {
        return this.saveOrUpdateIdempotency(entity, lock, lockKey, countWrapper, null);
    }
}