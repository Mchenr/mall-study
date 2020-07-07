package com.chenj.mall.controller;

import com.chenj.mall.common.api.CommonPage;
import com.chenj.mall.common.api.CommonResult;
import com.chenj.mall.mbg.model.PmsProductAttribute;
import com.chenj.mall.mbg.model.PmsProductAttributeCategory;
import com.chenj.mall.service.PmsProductAttributeCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(tags = "商品类型管理")
@RequestMapping("/productAttrCategory")
public class PmsAttributeCategoryController {

    @Autowired
    private PmsProductAttributeCategoryService productAttributeCategoryService;


    @ApiOperation("分页获取商品类型列表")
    @RequestMapping(value = "/listAttrCate", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult listAll(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<PmsProductAttributeCategory> productAttributeCategoryList = productAttributeCategoryService.list(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(productAttributeCategoryList));
    }

    @ApiOperation("根据id获取商品类型")
    @RequestMapping(value = "/getItem/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getItem(@PathVariable Long id) {
        PmsProductAttributeCategory productAttributeCategory = productAttributeCategoryService.getItem(id);
        if (productAttributeCategory != null) {
            return CommonResult.success(productAttributeCategory);
        }
        return CommonResult.failed("该类型不存在");
    }

    @ApiOperation("根据id更新商品类型")
    @RequestMapping(value = "/updateAttrCate/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateAttrCate(@PathVariable Long id,
                                @RequestParam("attrCateName") String attrCateName) {
        CommonResult commonResult;
        int count = productAttributeCategoryService.updateItem(id, attrCateName);
        if (count == 1){
            commonResult = CommonResult.success(count);
        }else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }

    @ApiOperation("新增商品类型")
    @RequestMapping(value = "/createAttrCate", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createAttrCate(@RequestParam("attrCateName") String attrCateName) {
        CommonResult commonResult;
        int count = productAttributeCategoryService.createItem(attrCateName);
        if (count == 1){
            commonResult = CommonResult.success(count);
        }else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }

    @ApiOperation("根据id删除商品类型")
    @RequestMapping(value = "/deleteAttrCate/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteAttrCate(@PathVariable Long id) {
        CommonResult commonResult;
        int count = productAttributeCategoryService.deleteItem(id);
        if (count == 1){
            commonResult = CommonResult.success(count);
        }else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }
}
