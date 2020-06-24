package com.chenj.mall.controller;

import com.chenj.mall.common.api.CommonPage;
import com.chenj.mall.common.api.CommonResult;
import com.chenj.mall.dto.PmsProductCategoryParam;
import com.chenj.mall.mbg.model.PmsBrand;
import com.chenj.mall.mbg.model.PmsProductCategory;
import com.chenj.mall.service.PmsProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 商品分类管理Controller
 * Created by chen on 2020/06/24.
 */

@RestController
@Api(tags = "商品分类管理")
@RequestMapping("/productCategory")
public class PmsProductCategoryController {

    @Autowired
    private PmsProductCategoryService pmsProductCategoryService;

//    @PreAuthorize("hasAuthority('pms:productCategory:create')")
    @ApiOperation("添加商品分类")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult create(@Validated @RequestBody PmsProductCategoryParam pmsProductCategoryParam,
                               BindingResult result) {
        int count = pmsProductCategoryService.create(pmsProductCategoryParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("删除商品分类")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public CommonResult delete(@PathVariable Long id) {
        int count = pmsProductCategoryService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("更新商品分类")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id,
                               @Validated @RequestBody PmsProductCategoryParam pmsProductCategoryParam,
                               BindingResult result) {
        int count = pmsProductCategoryService.update(id, pmsProductCategoryParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }
//    @PreAuthorize("hasAuthority('pms:productCategory:read')")
    @ApiOperation("分页查询商品分类列表")
    @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
    public CommonResult<CommonPage<PmsProductCategory>> getList(@PathVariable Long parentId,
                                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<PmsProductCategory> productCategoryList = pmsProductCategoryService.getList(parentId, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(productCategoryList));
    }

    @ApiOperation("根据id查询分类信息")
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult<PmsBrand> getItem(@PathVariable Long id){
        CommonResult commonResult;
        PmsProductCategory productCategory = pmsProductCategoryService.getItem(id);
        if (productCategory != null){
            commonResult = CommonResult.success(productCategory);
        }else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "修改显示状态")
    @RequestMapping(value = "/update/showStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateShowStatus(@RequestParam("id") Long id,
                                         @RequestParam("showStatus") Integer showStatus){
        CommonResult commonResult;
        int count = pmsProductCategoryService.updateShowStatus(id, showStatus);
        if (count > 0){
            commonResult = CommonResult.success(count);
        }else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "修改导航显示状态")
    @RequestMapping(value = "/update/navStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateNavStatus(@RequestParam("id") Long id,
                                         @RequestParam("showStatus") Integer showStatus){
        CommonResult commonResult;
        int count = pmsProductCategoryService.updateNavStatus(id, showStatus);
        if (count > 0){
            commonResult = CommonResult.success(count);
        }else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }
}
