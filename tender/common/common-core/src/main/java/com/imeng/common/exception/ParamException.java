package com.imeng.common.exception;

/**
 * 參數异常
 *
 * @author cicada
 */
public class ParamException extends RuntimeException {

    private static final long serialVersionUID = 6610083281801529147L;

    public ParamException(String message) {
        super(message);
    }
}
