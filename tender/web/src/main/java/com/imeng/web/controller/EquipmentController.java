package com.imeng.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import com.imeng.web.model.Equipment;
import com.imeng.web.service.IEquipmentService;
import com.imeng.common.model.PageResult;
import com.imeng.common.model.Result;

/**
 * 
 *
 * @author tw
 * @date 2020-09-03
 */
@Slf4j
@RestController
@RequestMapping("/equipment")
@Api(tags = "设备管理Api")
public class EquipmentController {
    @Autowired
    private IEquipmentService equipmentService;

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
        return equipmentService.findList(params);
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @GetMapping("/{id}")
    public Result findEquipmentById(@PathVariable Long id) {
        Equipment model = equipmentService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "保存")
    @PostMapping
    public Result save(@RequestBody Equipment equipment) {
            equipmentService.saveOrUpdate(equipment);
        return Result.succeed("保存成功");
    }

    /**
     * 删除 实际是修改delFlag字段
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        Equipment entity = new Equipment();
        entity.setId(id);
        entity.setDelFlag(1);
        equipmentService.updateById(entity);
        return Result.succeed("删除成功");
    }
}