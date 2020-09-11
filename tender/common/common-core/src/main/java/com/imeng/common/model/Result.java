package com.imeng.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: zlt
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    @JsonProperty("result")
    private T data;

    @JsonProperty("subCode")
    private Integer resp_code=CodeEnum.SUCCESS.getCode();

    @JsonProperty("subMsg")
    private String resp_msg;

    private Integer code;

    private String msg;

    public static <T> Result<T> succeed(String msg) {
        return succeedWith(null, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> succeed() {
        return succeedWith(null, CodeEnum.SUCCESS.getCode(), "成功");
    }

    public static <T> Result<T> succeed(T model, String msg) {
        return succeedWith(model, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> succeed(T model) {
        return succeedWith(model, CodeEnum.SUCCESS.getCode(), "");
    }

    public static <T> Result<T> succeedWith(T datas, Integer code, String msg) {
        return new Result<T>(datas, code, msg,code,msg);
    }

    public static <T> Result<T> failed(String msg) {
        return failedWith(null, CodeEnum.ERROR.getCode(), msg);
       /* //@ TODO 修改 统一fail抛出异常交由advice处理
        throw new BusinessException(msg);*/
    }

    public static <T> Result<T> failed(T model, String msg) {
        return failedWith(model, CodeEnum.ERROR.getCode(), msg);
    }

    public static <T> Result<T> failedWith(T datas, Integer code, String msg) {
        return new Result<>(datas, code, msg,code,msg);
    }

    public boolean isSuccess() {
        return resp_code.intValue() == CodeEnum.SUCCESS.getCode().intValue();
    }
}
