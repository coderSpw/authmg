package com.spw.authmg.backup.controller;

import com.spw.authmg.backup.service.BackUpService;
import com.spw.authmg.core.http.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.List;

/**
 * @Description 数据备份与还原
 * @Author spw
 * @Date 2021/4/5
 */
@Api(tags = "数据备份与还原")
@RestController
public class BackUpController {
    /**
     * 数据备份
     * @param database 数据库名称
     * @return
     */
    @GetMapping("/backUp")
    @ApiOperation("数据备份")
    @ApiParam(name =  "database", value ="数据库名称", required = true)
    public RespResult backUp(@NotNull @RequestParam String database) {
        Boolean success = backUpService.backUp(database, false);
        if (success) {
            return RespResult.sucessResult();
        }
        return RespResult.failResult("备份失败");
    }

    @Autowired
    private BackUpService backUpService;


    /**
     * 数据还原
     * @return
     */
    @GetMapping("/restore")
    @ApiOperation("数据还原")
    @ApiParam(name =  "name", value ="数据库名称", required = true)
    public RespResult restore(@NotNull @RequestParam String database) {
        Boolean success = backUpService.restore(database);
        if (success) {
            return RespResult.sucessResult();
        }
        return RespResult.failResult("还原失败");
    }

    /**
     * 查询备份文件名称列表
     * @return
     */
    @GetMapping("/findByName")
    @ApiOperation("根据数据库名称查询文件列表")
    @ApiParam(name =  "name", value ="数据库名称", required = true)
    public RespResult findByName(@NotNull @RequestParam String name) {

        List<String> fileNames = backUpService.findByName(name);
        return RespResult.sucessResult(fileNames);
    }

    /**
     * 删除文件
     * @param name
     * @return
     */
    @DeleteMapping("/deleteByName")
    @ApiOperation("删除文件")
    @ApiParam(name =  "name", value ="数据库名称", required = true)
    public RespResult deleteByName(@NotNull @RequestParam String name) {
        backUpService.deleteByName(name);
        return RespResult.sucessResult();
    }
}
