package com.chenj.mall.controller;

import com.chenj.mall.common.api.CommonPage;
import com.chenj.mall.common.api.CommonResult;
import com.chenj.mall.dto.PmsBrandParam;
import com.chenj.mall.mbg.model.PmsBrand;
import com.chenj.mall.service.PmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌功能Controller
 * Created by macro on 2018/4/26.
 */
@Controller
@Api(tags = "商品品牌管理")
@RequestMapping("/brand")
public class PmsBrandController {

    @Autowired
    private PmsBrandService pmsBrandService;

//    @PreAuthorize("hasAnyAuthority('pms:brand:read')")
    @ApiOperation("获取全部品牌列表")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PmsBrand>> getList(){
        return CommonResult.success(pmsBrandService.listAllBrand());
    }

    @ApiOperation("添加品牌")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@Validated @RequestBody PmsBrandParam pmsBrandParam, BindingResult result){
        CommonResult commonResult;
        int count = pmsBrandService.createBrand(pmsBrandParam);
        if (count == 1){
            commonResult = CommonResult.success(count);
        }else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }

    @ApiOperation("更新品牌")
    @ResponseBody
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id,
                               @Validated @RequestBody PmsBrandParam pmsBrandParam,
                               BindingResult result){
        CommonResult commonResult;
        int count = pmsBrandService.updateBrand(id, pmsBrandParam);
        if (count == 1){
            commonResult = CommonResult.success(count);
        }else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }

    @ApiOperation("删除品牌")
    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public CommonResult delete(@PathVariable Long id){
        CommonResult commonResult;
        int count = pmsBrandService.deleteBrand(id);
        if (count == 1){
            commonResult = CommonResult.success(count);
        }else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }

    @ApiOperation("根据品牌名称分页获取品牌列表")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<PmsBrand>> getList(@RequestParam(value = "keyword", required = false) String keyword,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){
        List<PmsBrand> pmsBrands = pmsBrandService.listBrand(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(pmsBrands));
    }

    @ApiOperation("根据编号查询品牌信息")
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult<PmsBrand> getItem(@PathVariable Long id){
        CommonResult commonResult;
        PmsBrand pmsBrand = pmsBrandService.getBrand(id);
        if (pmsBrand != null){
            commonResult = CommonResult.success(pmsBrand);
        }else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "批量删除品牌")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deleteBatch(@RequestParam("ids") List<Long> ids){
        CommonResult commonResult;
        int count = pmsBrandService.deleteBrand(ids);
        if (count > 0){
            commonResult = CommonResult.success(count);
        }else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "批量修改显示状态")
    @RequestMapping(value = "/update/showStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateShowStatus(@RequestParam("ids") List<Long> ids,
                                         @RequestParam("showStatus") Integer showStatus){
        CommonResult commonResult;
        int count = pmsBrandService.updateShowStatus(ids, showStatus);
        if (count > 0){
            commonResult = CommonResult.success(count);
        }else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }

    @ApiOperation(value = "批量更新厂家制造商状态")
    @RequestMapping(value = "/update/factoryStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateFactoryStatus(@RequestParam("ids") List<Long> ids,
                                            @RequestParam("factoryStatus") Integer factoryStatus){
        CommonResult commonResult;
        int count = pmsBrandService.updateFactoryStatus(ids, factoryStatus);
        if (count > 0){
            commonResult = CommonResult.success(count);
        }else {
            commonResult = CommonResult.failed();
        }
        return commonResult;
    }

}
