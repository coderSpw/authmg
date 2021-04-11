package com.spw.authmg.controller;

import com.spw.authmg.core.http.RespResult;
import com.spw.authmg.core.page.PageRequest;
import com.spw.authmg.pojo.SysLoginLog;
import com.spw.authmg.service.SysLoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 登陆日志
 * @author spw
 * @date 2021/02/18
 */
@Api(tags = "登陆日志")
@RestController
@RequestMapping("/loginLog")
public class SysLoginLogController {
    @Autowired
    private SysLoginLogService sysLoginLogService;

    /**
     * 分页查询
     * @param pageRequest
     * @return
     */
    @ApiOperation("分页查询")
    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('sys:loginlog:view')")
    public RespResult findPage(@RequestBody PageRequest pageRequest){
        return RespResult.sucessResult(sysLoginLogService.findPage(pageRequest));
    }

    /**
     * 删除登陆日志
     * @param sysLoginLogs
     * @return
     */
    @ApiOperation("删除登陆日志")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('sys:loginlog:delete')")
    public RespResult delete(@RequestBody List<SysLoginLog> sysLoginLogs){
        return RespResult.sucessResult(sysLoginLogService.delete(sysLoginLogs));
    }
}
