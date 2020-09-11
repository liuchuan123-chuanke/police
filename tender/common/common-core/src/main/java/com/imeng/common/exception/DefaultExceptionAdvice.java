package com.imeng.common.exception;

import com.imeng.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * 异常通用处理
 *
 * @author zlt
 */
@ResponseBody
@Slf4j
public class DefaultExceptionAdvice {

    /**
     * IllegalArgumentException异常处理返回json
     * 返回状态码:400
     */
    /*@ResponseStatus(HttpStatus.BAD_REQUEST)*/
    @ExceptionHandler({IllegalArgumentException.class})
    public Result badRequestException(IllegalArgumentException e) {
        return defHandler(HttpStatus.BAD_REQUEST.value(), "参数解析失败:"+e.getMessage(), e);
    }

    /**
     * AccessDeniedException异常处理返回json
     * 返回状态码:403
     */
    /*@ResponseStatus(HttpStatus.FORBIDDEN)*/
    @ExceptionHandler({AccessDeniedException.class})
    public Result badMethodExpressException(AccessDeniedException e) {
        return defHandler(HttpStatus.FORBIDDEN.value(), "没有权限请求当前方法", e);
    }

    /**
     * 返回状态码:405
     */
    /* @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)*/
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return defHandler(HttpStatus.METHOD_NOT_ALLOWED.value(), "不支持当前请求方法", e);
    }

    /**
     * 返回状态码:415
     */
    /*@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)*/
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public Result handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return defHandler(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "不支持当前媒体类型", e);
    }
    /**
     * 幂等性异常统一处理
     * @param e
     * @return
     *//*
    @ExceptionHandler({IdempotencyException.class})
    public Result handleIdempotency(IdempotencyException e){
        return defHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage(),e);
    }*/

    /**
     * SQLException sql异常处理
     * 返回状态码:500
     */
    /*@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)*/
    @ExceptionHandler({SQLException.class})
    public Result handleSQLException(SQLException e) {
        return defHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务运行SQLException异常", e);

    }

    /**
     * IdempotencyException 幂等性异常
     * 返回状态码:200
     *
     * @TODO 思考
     */
    /*@ResponseStatus(HttpStatus.OK)*/
    @ExceptionHandler(IdempotencyException.class)
    public Result handleException(IdempotencyException e) {
        return defHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e);
    }

    /**
     * BusinessException 业务异常处理
     * 返回状态码:500
     */
    /* @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)*/
    @ExceptionHandler(BusinessException.class)
    public Result handleException(BusinessException e) {
        return defHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(), "业务异常:"+e.getMessage(), e);
    }

    /**
     * 所有异常统一处理
     * 返回状态码:500
     */
    /*@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)*/
    @ExceptionHandler(NullPointerException.class)
    public Result handleNull(NullPointerException e) {
        e.printStackTrace();
        return defHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统异常", e);
    }

    @ExceptionHandler(ParamException.class)
    public Result handleException(ParamException e) {
        return defHandler(HttpStatus.OK.value(), ":"+e.getMessage(), e);
    }
    /**
     * 所有异常统一处理
     * 返回状态码:500
     */
    /*@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)*/
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return defHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统异常:"+e.getMessage(), e);
    }


    protected Result defHandler(Integer code, String msg, Exception e) {
        log.error(msg, e);
        return Result.failedWith(null, code, msg);
    }
}
