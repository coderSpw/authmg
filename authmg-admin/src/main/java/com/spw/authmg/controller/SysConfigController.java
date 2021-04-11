package com.spw.authmg.controller;

import com.spw.authmg.core.http.RespResult;
import com.spw.authmg.core.page.PageRequest;
import com.spw.authmg.pojo.SysConfig;
import com.spw.authmg.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统配置
 * @author spw
 * @date 2021/02/18
 */
@Api(tags = "系统配置")
@RestController
@RequestMapping("/config")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 保存配置
     * @param sysConfig
     * @return
     */
    @ApiOperation("保存配置")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:config:save') and hasAuthority('sys:config:update')")
    public RespResult save(@RequestBody SysConfig sysConfig){
        return RespResult.sucessResult(sysConfigService.save(sysConfig));
    }

    /**
     * 删除配置
     * @param sysConfigList
     * @return
     */
    @ApiOperation("删除配置")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('sys:config:delete')")
    public RespResult delete(@RequestBody List<SysConfig> sysConfigList){
        return RespResult.sucessResult(sysConfigService.delete(sysConfigList));
    }

    /**
     * 分页查询
     * @param pageRequest
     * @return
     */
    @ApiOperation("分页查询")
    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('sys:config:view')")
    public RespResult findPage(@RequestBody PageRequest pageRequest){
        return RespResult.sucessResult(sysConfigService.findPage(pageRequest));
    }

    /**
     * 根据标签查询
     * @param label
     * @return
     */
    @ApiOperation("根据标签查询")
    @GetMapping("/findByLabel")
    @PreAuthorize("hasAuthority('sys:config:view')")
    public RespResult findByLabel(@RequestParam("label") String label){
        return RespResult.sucessResult(sysConfigService.findByLabel(label));
    }
}
