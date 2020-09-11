package com.imeng.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.imeng.common.model.SimpleClientDto;

/**
 * 租户holder
 * 不存明文密钥
 * @author zlt
 * @date 2019/8/5
 */
public class TenantContextHolder {

    /**
     * 支持父子线程之间的数据传递
     */
    private static final ThreadLocal<SimpleClientDto> CONTEXT = new TransmittableThreadLocal<>();

    public static void setTenant(SimpleClientDto tenant) {
        CONTEXT.set(tenant);
    }

    public static SimpleClientDto getTenant() {
        return CONTEXT.get();
    }

    public static String getTenantId() {
        return CONTEXT.get()==null?null:CONTEXT.get().getClientId();
    }
    public static String getTenantScope() {
        return CONTEXT.get()==null?null:CONTEXT.get().getScope();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
