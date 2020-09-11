package com.imeng.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.imeng.common.model.SimpleUserDto;

/**
 * 用户holder userId
 *
 * @author tw
 * 2020/1/17 15:35
 */
public class UserContextHolder {

    /**
     * 支持父子线程之间的数据传递
     */
    private static final ThreadLocal<SimpleUserDto> CONTEXT = new TransmittableThreadLocal<>();

    public static void setUser(SimpleUserDto user) {
        CONTEXT.set(user);
    }

    public static String getUserId() {
        return CONTEXT.get()==null?null:CONTEXT.get().getUserId();
    }
    /*public static String getUserStatus() {
        return CONTEXT.get()==null?null:CONTEXT.get().getStatus();
    }*/
    public static String getUserName(){
        return CONTEXT.get()==null?null:CONTEXT.get().getUserName();
    }
    public static String getRoleCode(){
        return CONTEXT.get()==null?null:CONTEXT.get().getRole();
    }
    public static String getUserFullName(){
        return CONTEXT.get()==null?null:CONTEXT.get().getFullName();
    }
    public static void clear() {
        CONTEXT.remove();
    }

}
