package com.chenj.mall.controller;

import com.chenj.mall.common.api.CommonPage;
import com.chenj.mall.common.api.CommonResult;
import com.chenj.mall.dto.SmsHomeRecommendSubjectParam;
import com.chenj.mall.mbg.model.SmsHomeRecommendSubject;
import com.chenj.mall.service.SmsHomeRecommendSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/home/recommendSubject")
@Api(tags = "首页专题推荐")
public class SmsHomeRecommendSubjectController {

    @Autowired
    private SmsHomeRecommendSubjectService homeRecommendSubjectService;

    @RequestMapping(value = "/getItem/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("根据Id获得首页专题推荐")
    public CommonResult getItem(@PathVariable Long id){
        SmsHomeRecommendSubject homeRecommendSubject = homeRecommendSubjectService.selectById(id);
        if (homeRecommendSubject != null){
            return CommonResult.success(homeRecommendSubject);
        }
        return CommonResult.failed();
    }

    @RequestMapping(value = "/getItem", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("根据专题Id和专题名称查询首页专题推荐(只作为测试choose用，后期可以用来增加一个根据专题Id或名称快捷查询某一个专题推荐，在Service层判断用户输入的是Id还是名称)")
    public CommonResult getItem(@RequestParam(value = "subjectId", required = false) Long subjectId,
                                @RequestParam(value = "subjectName", required = false) String subjectName){
        SmsHomeRecommendSubject homeRecommendSubject = homeRecommendSubjectService.selectBySubjectIdOrSubjectName(subjectId, subjectName);
        if (homeRecommendSubject != null){
            return CommonResult.success(homeRecommendSubject);
        }
        return CommonResult.failed("没有匹配的结果！");
    }

    @ApiOperation("分页查询首页专题推荐列表")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){
        List<SmsHomeRecommendSubject> homeRecommendSubjects = homeRecommendSubjectService.list(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(homeRecommendSubjects));
    }

    @ApiOperation("根据专题名称和推荐状态组合条件查询")
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public CommonResult search(@RequestParam(value = "subjectName", required = false) String subjectName,
                               @RequestParam(value = "recommendStatus", required = false) Integer recommendStatus,
                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){

        List<SmsHomeRecommendSubject> homeRecommendSubjects = homeRecommendSubjectService.selectBySubjectNameOrRecommendStatus(subjectName, recommendStatus, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(homeRecommendSubjects));
    }

    @ApiOperation("新增首页标题推荐")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody SmsHomeRecommendSubjectParam smsHomeRecommendSubjectParam){
        int count = homeRecommendSubjectService.insert(smsHomeRecommendSubjectParam);
        if (count == 1){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("批量新增首页标题推荐")
    @RequestMapping(value = "/createList", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody List<SmsHomeRecommendSubjectParam> smsHomeRecommendSubjectParams){
        int count = homeRecommendSubjectService.insertList(smsHomeRecommendSubjectParams);
        if (count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("根据主键更新")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody SmsHomeRecommendSubjectParam smsHomeRecommendSubjectParam){

        int count = homeRecommendSubjectService.updateById(id, smsHomeRecommendSubjectParam);
        if (count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("批量更新推荐状态")
    @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@RequestParam List<Long> ids, @RequestParam Integer recommendStatus){
        int count = homeRecommendSubjectService.updateRecommendStatus(ids, recommendStatus);
        if (count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("根据主键删除")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@PathVariable Long id){
        SmsHomeRecommendSubject smsHomeRecommendSubject = homeRecommendSubjectService.selectById(id);
        if (null == smsHomeRecommendSubject){
            return CommonResult.failed();
        }
        int count = homeRecommendSubjectService.deleteById(id);
        if (count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("批量删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@RequestParam List<Long> ids){
        int count = homeRecommendSubjectService.deleteByIds(ids);
        if (count > 0){
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
