package com.chenj.mall.controller;

import com.chenj.mall.common.api.CommonPage;
import com.chenj.mall.common.api.CommonResult;
import com.chenj.mall.dto.PmsAttributeParam;
import com.chenj.mall.mbg.model.PmsProductAttribute;
import com.chenj.mall.service.PmsAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(tags = "商品属性管理")
@RequestMapping("/productAttr")
public class PmsAttributeController {

    @Autowired
    private PmsAttributeService pmsAttributeService;

    @ApiOperation("分页获取商品属性或参数列表")
    @RequestMapping(value = "/productAttrList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult listAll(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                @RequestParam(value = "type") Integer type,
                                @RequestParam(value = "cid") Long cid) {
        List<PmsProductAttribute> productAttributeList = pmsAttributeService.list(pageNum, pageSize, cid, type);
        return CommonResult.success(CommonPage.restPage(productAttributeList));
    }

    @ApiOperation("根据id获取商品属性（参数）")
    @RequestMapping(value = "/getItem/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getItem(@PathVariable Long id) {
        PmsProductAttribute productAttribute = pmsAttributeService.getItem(id);
        if (productAttribute != null){
            return CommonResult.success(productAttribute);
        }
        return CommonResult.failed("该类型不存在");
    }

    @ApiOperation("根据id更新商品属性（参数）")
    @RequestMapping(value = "/updateProductAttr/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateProductAttr(@PathVariable Long id,
                                          @RequestBody PmsAttributeParam pmsAttributeParam) {
        CommonResult commonResult;
        int count = pmsAttributeService.updateItem(id, pmsAttributeParam);
        if (count == 1){
            commonResult = CommonResult.success(count);
        }else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }

    @ApiOperation("新增商品属性（参数）")
    @RequestMapping(value = "/addProductAttr", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult addProductAttr(@Validated @RequestBody PmsAttributeParam pmsAttributeParam,
                                       BindingResult result) {
        CommonResult commonResult;
        int count = pmsAttributeService.createItem(pmsAttributeParam);
        if (count == 1){
            commonResult = CommonResult.success(count);
        }else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }

    @ApiOperation("根据id删除商品属性（参数）")
    @RequestMapping(value = "/deleteAttr/batch", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deleteAttrCate(@RequestParam("ids") List<Long> ids) {
        CommonResult commonResult;
        int count = pmsAttributeService.deleteItem(ids);
        if (count >= 1){
            commonResult = CommonResult.success(count);
        }else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }

}
