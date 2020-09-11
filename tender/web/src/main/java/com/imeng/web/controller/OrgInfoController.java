package com.imeng.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import com.imeng.web.model.OrgInfo;
import com.imeng.web.service.IOrgInfoService;
import com.imeng.common.model.PageResult;
import com.imeng.common.model.Result;

/**
 * 单位信息表
 *
 * @author tw
 * @date 2020-09-03
 */
@Slf4j
@RestController
@RequestMapping("/orginfo")
@Api(tags = "单位信息表")
public class OrgInfoController {
    @Autowired
    private IOrgInfoService orgInfoService;

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
        return orgInfoService.findList(params);
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @GetMapping("/{id}")
    public Result findOrgInfoById(@PathVariable Long id) {
        OrgInfo model = orgInfoService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "保存")
    @PostMapping
    public Result save(@RequestBody OrgInfo orgInfo) {
    Assert.hasText(orgInfo.getOrgName(),"");
    Assert.hasText(orgInfo.getOrgName(),"");
    orgInfoService.saveOrUpdate(orgInfo);
    return Result.succeed("保存成功");
    }

    /**
     * 删除 实际是修改delFlag字段
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        OrgInfo entity = new OrgInfo();
        entity.setId(id);
        entity.setDelFlag(1);
        orgInfoService.updateById(entity);
        return Result.succeed("删除成功");
    }
}
