package com.chenj.mall.controller;

import com.chenj.mall.common.api.CommonPage;
import com.chenj.mall.common.api.CommonResult;
import com.chenj.mall.dto.UmsAdminParam;
import com.chenj.mall.mbg.model.UmsAdmin;
import com.chenj.mall.mbg.model.UmsPermission;
import com.chenj.mall.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台用户管理
 * Created by macro on 2018/4/26.
 */

@Controller
@Api(tags = "后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {

    @Autowired
    private UmsAdminService umsAdminService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation("用户注册")
    public CommonResult register(@Validated @RequestBody UmsAdminParam adminParam, BindingResult result){
        UmsAdmin admin = umsAdminService.register(adminParam);
        if (admin != null){
            return CommonResult.success(admin);
        }
        return CommonResult.failed();
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation("用户登录并返回token")
    public CommonResult login(@Validated @RequestBody UmsAdminParam umsAdminParam, BindingResult result){
        String token = umsAdminService.login(umsAdminParam.getUsername(), umsAdminParam.getPassword());
        if (token == null) {
            return CommonResult.failed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ApiOperation("通过token返回用户最新数据")
    public CommonResult info(@RequestParam(value = "token") String token){
        if (token == null) {
            return CommonResult.failed("token不存在");
        }
        UmsAdmin admin = new UmsAdmin();
        admin.setUsername("admin");
        admin.setIcon("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return CommonResult.success(admin);
    }

    @ResponseBody
    @ApiOperation("更新指定用户信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult update(@PathVariable Long id, @RequestBody UmsAdmin admin){
        int count = umsAdminService.update(id, admin);
        if (count > 0){
            return CommonResult.success(count);
        }else {
            return CommonResult.failed();
        }
    }

    @ResponseBody
    @ApiOperation("删除指定用户信息")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public CommonResult delete(@PathVariable Long id){
        int count = umsAdminService.delete(id);
        if (count > 0){
            return CommonResult.success(count);
        }else {
            return CommonResult.failed();
        }
    }

    @ResponseBody
    @ApiOperation("根据用户名或昵称获取用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list(@RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
        List<UmsAdmin> adminList = umsAdminService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(adminList));
    }

    @ResponseBody
    @ApiOperation("获取用户所有权限")
    @RequestMapping(value = "/permission/{adminId}", method = RequestMethod.GET)
    public CommonResult<List<UmsPermission>> getPermissionList(@PathVariable Long adminId){
        List<UmsPermission> permissionList = umsAdminService.getPermissionList(adminId);
        return CommonResult.success(permissionList);
    }
}
