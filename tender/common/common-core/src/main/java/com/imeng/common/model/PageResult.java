package com.imeng.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

/**
 * 分页实体类   修改返回提 与Result一致
 *
 * @author zlt
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = -275582248840137389L;

    /**
     * 总数
     */
    private Long count;

    @JsonProperty("code")
    @Builder.Default
    private int code = HttpStatus.OK.value();

    @JsonProperty("success")
    @Builder.Default
    private boolean success = true;

    private String bookMark;

    /**
     * 消息
     */
    @Builder.Default
    private String resp_msg = "success";

    /**
     * 当前页结果集
     */
    @JsonProperty("result")
    private List<T> data;

    public static PageResult success(long count,List data){
        return new PageResult(count,200,data);
    }

    public PageResult(Long count, int code, List<T> data) {
        this.count = count;
        this.code = code;
        this.data = data;
    }

}
