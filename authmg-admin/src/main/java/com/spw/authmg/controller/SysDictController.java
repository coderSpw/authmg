package com.spw.authmg.controller;

import com.spw.authmg.core.http.RespResult;
import com.spw.authmg.core.page.PageRequest;
import com.spw.authmg.pojo.SysDict;
import com.spw.authmg.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 字典管理
 * @author spw
 * @date 2021/02/18
 */
@Api(tags = "字典管理")
@RestController
@RequestMapping("/dict")
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;

    /**
     * 添加数据
     * @param sysDict
     * @return
     */
    @ApiOperation("添加数据")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:dict:save') and hasAuthority('sys:dict:update')")
    public RespResult save(@RequestBody SysDict sysDict){
        return RespResult.sucessResult(sysDictService.save(sysDict));
    }

    /**
     * 删除指定数据
     * @param sysDictlist
     * @return
     */
    @ApiOperation("删除指定数据")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('sys:dict:delete')")
    public RespResult delete(@RequestBody List<SysDict> sysDictlist){
        return RespResult.sucessResult(sysDictService.delete(sysDictlist));
    }


    /**
     * 分页查询
     * @param pageRequest
     * @return
     */
    @ApiOperation("分页查询")
    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('sys:dict:view')")
    public RespResult findPage(@RequestBody PageRequest pageRequest){
        return RespResult.sucessResult(sysDictService.findPage(pageRequest));
    }

    /**
     * 根据标签模糊查询
     * @param label
     * @return
     */
    @ApiOperation("根据标签模糊查询")
    @GetMapping("/findByLabel")
    @PreAuthorize("hasAuthority('sys:dict:view')")
    public RespResult findByLabel(@RequestParam String label){
        return RespResult.sucessResult(sysDictService.findByLabel(label));
    }

}
