package com.chenj.mall.controller;

import com.chenj.mall.common.api.CommonResult;
import com.chenj.mall.dto.SmsHomeRecommendProductParam;
import com.chenj.mall.mbg.model.SmsHomeRecommendProduct;
import com.chenj.mall.service.SmsHomeRecommendProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/home/recommendProduct")
@Api(tags = "首页人气推荐")
public class SmsHomeRecommendProductController {

    @Autowired
    private SmsHomeRecommendProductService homeRecommendProductService;

    @RequestMapping(value = "/getItem/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("根据Id获得首页人气推荐")
    public CommonResult getItem(@PathVariable Long id){
        SmsHomeRecommendProduct homeRecommendProduct = homeRecommendProductService.selectById(id);
        if (homeRecommendProduct != null){
            return CommonResult.success(homeRecommendProduct);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取首页人气推荐列表")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list(){
        List<SmsHomeRecommendProduct> homeRecommendProducts = homeRecommendProductService.selectAll();
        return CommonResult.success(homeRecommendProducts);
    }

    @ApiOperation("根据人气名称和推荐状态组合条件查询(暂未实现)")
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public CommonResult search(@RequestParam(value = "productName", required = false) String productName,
                               @RequestParam(value = "recommendStatus",required = false) Integer recommendStatus){

        List<SmsHomeRecommendProduct> homeRecommendProducts = homeRecommendProductService.selectByProductNameOrRecommendStatus(productName,recommendStatus);
        return CommonResult.success(homeRecommendProducts);
    }

    @ApiOperation("新增首页人气推荐")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody SmsHomeRecommendProductParam smsHomeRecommendProductParam){
        int count = homeRecommendProductService.insert(smsHomeRecommendProductParam);
        if (count == 1){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("根据主键更新")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody SmsHomeRecommendProductParam smsHomeRecommendProductParam){

        int count = homeRecommendProductService.updateById(id, smsHomeRecommendProductParam);
        if (count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("根据主键删除")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@PathVariable Long id){
        SmsHomeRecommendProduct smsHomeRecommendProduct = homeRecommendProductService.selectById(id);
        if (null == smsHomeRecommendProduct){
            return CommonResult.failed();
        }
        int count = homeRecommendProductService.deleteById(id);
        if (count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
