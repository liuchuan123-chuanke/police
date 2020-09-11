package com.imeng.common.model.base;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @author wwj
 * 2020/1/8 19:38
 */
@Data
public class BaseDto {

    //id
    private Long id;

    //id数组
    private Long[] ids;

    //页面
    private Integer page;

    //限制
    private Integer limit;

    //类型
    private Integer type;

    //状态
    private Integer status;

    //关键字
    private String keyword;

    //是否通过 1通过 0 未通过
    private Integer pass = 1;

    //备注 说明
    private String remark;

    //删除标识
    private Integer delFlag;

    //排序字段
    private String orderField;

    //排序 DESC ASC两种值
    private String order;

    public static void main(String[] args) {
        BaseDto dto = new BaseDto();
        Long[] s = null;
        dto.setIds(s);

        System.out.println(StringUtils.trimAllWhitespace("车 市"));
    }

    //去除关键字的空格进行查询
    public String getKeyword() {
       return keyword;
    }

    public void setKeyword(String keyword) {
        if(!StringUtils.isEmpty(keyword)){
            //trim只能去掉半角的空格
            keyword=StringUtils.trimAllWhitespace(keyword);
        }
        this.keyword = keyword;
    }
}
