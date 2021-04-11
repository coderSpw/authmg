package com.spw.authmg.controller;

import com.spw.authmg.core.http.RespResult;
import com.spw.authmg.pojo.SysDept;
import com.spw.authmg.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 机构管理
 * @author spw
 * @date 2021/02/15
 */
@Api(tags = "机构管理")
@RestController
@RequestMapping("dept")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 保存机构
     * @param sysDept
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:dept:save') and hasAuthority('sys:dept:update')")
    @ApiOperation(value = "保存机构",httpMethod = "POST")
    public RespResult save(@RequestBody SysDept sysDept) {
        return RespResult.sucessResult(sysDeptService.save(sysDept));
    }

    /**
     * 删除机构
     * @param depts
     * @return
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('sys:dept:delete')")
    @ApiOperation(value = "删除机构", httpMethod = "DELETE")
    public RespResult delete(@RequestBody List<SysDept> depts) {
        return RespResult.sucessResult(sysDeptService.delete(depts));
    }

    /**
     * 查询机构树
     * @return
     */
    @GetMapping("/findTree")
    @PreAuthorize("hasAuthority('sys:dept:view')")
    @ApiOperation(value = "查询机构树", httpMethod = "GET")
    public RespResult findTree() {
        return RespResult.sucessResult(sysDeptService.findTree());
    }

}
