package com.spw.authmg.backup.controller;

import com.spw.authmg.backup.fegin.SysUserFeign;
import com.spw.authmg.core.http.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 用户管理
 * @Author spw
 * @Date 2021/4/11
 */
@Api(tags = "用户管理")
@RequestMapping("/backup")
@RestController
public class SysUserController {
    @Autowired
    private SysUserFeign sysUserFeign;

    @ApiOperation("查询全部用户")
    @GetMapping("/findAll")
    public RespResult findAll() {
        return sysUserFeign.findAll();
    }
}
