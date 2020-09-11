package com.imeng.web.controller;

import com.imeng.common.model.PageResult;
import com.imeng.common.model.Result;
import com.imeng.web.model.AttributeDetail;
import com.imeng.web.service.IAttributeDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *
 *
 * @author tw
 * @date 2020-02-20 16:43:51
 */
@Slf4j
@RestController
@RequestMapping("/attributedetail")
@Api(tags = "数据字典详细")
public class AttributeDetailController {
    @Autowired
    private IAttributeDetailService attributeDetailService;

    /**
     * 列表
     */
    @ApiOperation(value = "查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页起始位置", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "分页结束位置", required = true, dataType = "Integer")
    })
    @GetMapping
    public PageResult list(@RequestParam Map<String, Object> params) {
        return attributeDetailService.findList(params);
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @GetMapping("/{id}")
    public Result findAttributeDetailById(@PathVariable Long id) {
        AttributeDetail model = attributeDetailService.getById(id);
        return Result.succeed(model, "查询成功");
    }

  /*  *//**
     * 新增or更新
     *//*
    @ApiOperation(value = "保存")
    @PostMapping
    public Result save(@RequestBody AttributeDetail attributeDetail) {
        attributeDetailService.saveOrUpdate(attributeDetail);
        return Result.succeed("保存成功");
    }*/

    /**
     * 删除 实际是修改delFlag字段
     */
    @ApiOperation(value = "删除")
    @GetMapping("/del/{id}")
    public Result delete(@PathVariable Long id) {
        AttributeDetail entity = new AttributeDetail();
        entity.setId(id);
        entity.setDelFlag(1);
        attributeDetailService.updateById(entity);
        return Result.succeed("删除成功");
    }

    /**
     * 数据字典详情列表子级
     * @param id
     * @return
     */
    @ApiOperation(value = "查询字典下详细")
    @DeleteMapping("/detail/{id}")
    public Result queryByAttrId(@PathVariable("id") Long id){
        Assert.notNull(id,"非法参数");
        return Result.succeed(attributeDetailService.queryByParam(id));
    }
}
