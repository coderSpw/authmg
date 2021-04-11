package com.spw.authmg.controller;

import com.spw.authmg.core.http.RespResult;
import com.spw.authmg.pojo.SysMenu;
import com.spw.authmg.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理
 * @author spw
 * @date 2021/02/18
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 保存菜单
     * @param sysMenu
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:menu:save') and hasAuthority('sys:menu:edit')")
    @ApiOperation(value = "保存菜单", httpMethod = "POST")
    public RespResult save(@RequestBody SysMenu sysMenu) {
        return RespResult.sucessResult(sysMenuService.save(sysMenu));
    }

    /**
     * 删除菜单
     * @param sysMenuList
     * @return
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @ApiOperation(value = "删除菜单", httpMethod = "DELETE")
    public RespResult delete(@RequestBody List<SysMenu> sysMenuList) {
        return RespResult.sucessResult(sysMenuService.delete(sysMenuList));
    }

    /**
     * 查询导航菜单树
     * @param userName
     * @return
     */
    @GetMapping("/findNavTree")
    @PreAuthorize("hasAuthority('sys:menu:view')")
    @ApiOperation(value = "查询导航菜单树", httpMethod = "GET")
    public RespResult findNavTree(@RequestParam String userName) {
        return RespResult.sucessResult(sysMenuService.findNavTree(userName));
    }

    /**
     * 查询导航菜单树
     * @return
     */
    @GetMapping("/findMenuTree")
    @PreAuthorize("hasAuthority('sys:menu:view')")
    @ApiOperation(value = "查询菜单树", httpMethod = "GET")
    public RespResult findMenuTree() {
        return RespResult.sucessResult(sysMenuService.findMenuTree());
    }
}
