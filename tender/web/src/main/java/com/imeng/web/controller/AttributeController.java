package com.imeng.web.controller;

import java.util.List;
import java.util.Map;

import com.imeng.common.Enum.DelFlagEnum;
import com.imeng.web.model.AttributeDetail;
import com.imeng.web.vo.AttributeVo;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import com.imeng.web.model.Attribute;
import com.imeng.web.service.IAttributeService;
import com.imeng.common.model.PageResult;
import com.imeng.common.model.Result;

/**
 * 字典表
 *
 * @author tw
 * @date 2020-09-07
 */
@Slf4j
@RestController
@RequestMapping("/attribute")
@Api(tags = "字典表")
public class AttributeController {
    @Autowired
    private IAttributeService attributeService;

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
        return attributeService.findList(params);
    }

    /**
     * 查询
     */
    @ApiOperation(value = "查询")
    @GetMapping("/{id}")
    public Result findAttributeById(@PathVariable Long id) {
        Attribute model = attributeService.getById(id);
        return Result.succeed(model, "查询成功");
    }

    /**
     * 新增or更新
     */
    @ApiOperation(value = "保存")
    @PostMapping
    public Result save(@RequestBody Attribute attribute) {
            attributeService.saveOrUpdate(attribute);
        return Result.succeed("保存成功");
    }

    /**
     * 删除 实际是修改delFlag字段
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        Attribute entity = new Attribute();
        entity.setId(id);
        entity.setDelFlag(1);
        attributeService.updateById(entity);
        return Result.succeed("删除成功");
    }

    /**
     * 按字典码加载字典参数
     * @param attrCode
     * @return
     */
    @ApiOperation(value = "按字典码加载字典参数")
    @GetMapping("/attrCode/{code}")
    public Result queryByCode(@PathVariable("code")@ApiParam("字典码 开头dic") String attrCode){
        Assert.isTrue(!StringUtils.isEmpty(attrCode),"非法参数");
        List<AttributeVo> list = attributeService.findListByParam(attrCode, DelFlagEnum.NO_DEL.getDelFlag());
        if(CollectionUtils.isEmpty(list)){
            return Result.failed("未知字典參數");
        }
        return Result.succeed(list.get(0));
    }


    /**
     * 按字典码里详情
     * @param
     * @return
     */
    @ApiOperation(value = "添加字典码详情   code说明:  comment_type:评价类别管理  fault_type:故障类别管理 ")
    @PostMapping("/attrCode/{code}/add")
    public Result addAtrDetail(@PathVariable("code") String code,@RequestBody AttributeDetail attribute){
        Assert.hasText(code,"配置地址错误");
        attributeService.addDetail(code,attribute);
        return Result.succeed();
    }
}
