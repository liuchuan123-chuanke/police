package com.imeng.redisStarter.lock;

import com.imeng.common.constant.CommonConstant;
import com.imeng.common.exception.LockException;
import com.imeng.common.lock.DistributedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.util.concurrent.TimeUnit;

/**
 * @author wwj
 * 2020/6/2 12:06
 */
@ConditionalOnClass(RedissonClient.class)
public class RedissonDistributedLock implements DistributedLock {
    @Autowired
    private RedissonClient redisson;

    private RLock getLock(String key, boolean isFair) {
        if (isFair) {
            return redisson.getFairLock(CommonConstant.LOCK_KEY_PREFIX + key);
        }
        return redisson.getLock(CommonConstant.LOCK_KEY_PREFIX + key);
    }

    @Override
    public RLock lock(String key, long leaseTime, TimeUnit unit, boolean isFair) {
        RLock lock = getLock(key, isFair);
        lock.lock(leaseTime, unit);
        return lock;
    }
    @Override
    public RLock lock(String key, long leaseTime, TimeUnit unit) {
        return lock(key, leaseTime, unit, false);
    }
    @Override
    public RLock lock(String key, boolean isFair) {
        return lock(key, -1, null, isFair);
    }
    @Override
    public RLock lock(String key) {
        return lock(key, -1, null, false);
    }

    @Override
    public RLock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit, boolean isFair) throws InterruptedException {
        RLock lock = getLock(key, isFair);
        if (lock.tryLock(waitTime, leaseTime, unit)) {
            return lock;
        }
        return null;
    }
    @Override
    public RLock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
        return tryLock(key, waitTime, leaseTime, unit, false);
    }
    @Override
    public RLock tryLock(String key, long waitTime, TimeUnit unit, boolean isFair) throws InterruptedException {
        return tryLock(key, waitTime, -1, unit, isFair);
    }
    @Override
    public RLock tryLock(String key, long waitTime, TimeUnit unit) throws InterruptedException {
        return tryLock(key, waitTime, -1, unit, false);
    }

    @Override
    public void unlock(Object lock) {
        if (lock != null) {
            if (lock instanceof RLock) {
                RLock rLock = (RLock)lock;
                if (rLock.isLocked()) {
                    rLock.unlock();
                }
            } else {
                throw new LockException("requires RLock type");
            }
        }
    }
}
