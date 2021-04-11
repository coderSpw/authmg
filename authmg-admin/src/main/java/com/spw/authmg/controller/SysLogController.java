package com.spw.authmg.controller;

import com.spw.authmg.core.http.RespResult;
import com.spw.authmg.core.page.PageRequest;
import com.spw.authmg.pojo.SysLog;
import com.spw.authmg.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 日志管理
 * @author spw
 * @date 2021/02/18
 */
@Api(tags = "日志管理")
@RestController
@RequestMapping("/log")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;


    /**
     * 分页查询
     * @param pageRequest
     * @return
     */
    @ApiOperation("分页查询")
    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('sys:log:view')")
    public RespResult findPage(@RequestBody PageRequest pageRequest){
        return RespResult.sucessResult(sysLogService.findPage(pageRequest));
    }

    /**
     * 删除操作日志
     * @param sysLogs
     * @return
     */
    @ApiOperation("删除操作日志")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('sys:log:delete')")
    public RespResult delete(@RequestBody List<SysLog> sysLogs){
        return RespResult.sucessResult(sysLogService.delete(sysLogs));
    }


}
