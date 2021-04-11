package com.spw.authmg.controller;

import com.spw.authmg.constant.Constants;
import com.spw.authmg.core.http.RespResult;
import com.spw.authmg.core.page.PageRequest;
import com.spw.authmg.pojo.SysRole;
import com.spw.authmg.pojo.SysRoleMenu;
import com.spw.authmg.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理
 * @author spw
 * @date 2021/02/18
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 保存角色
     * @param sysRole
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:role:add') and hasAuthority('sys:role:edit')")
    @ApiOperation(value = "保存角色",httpMethod = "POST")
    public RespResult save(@RequestBody SysRole sysRole) {
        //根据id查询当前角色
        SysRole role = sysRoleService.findById(sysRole.getId());
        if (Constants.ADMIN.equalsIgnoreCase(role.getName())) {
            return RespResult.failResult("超级管理员不允许修改");
        }
        return RespResult.sucessResult(sysRoleService.save(sysRole));
    }

    /**
     * 删除角色
     * @param sysRoleList
     * @return
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @ApiOperation(value = "删除角色", httpMethod = "DELETE")
    public RespResult delete(@RequestBody List<SysRole> sysRoleList) {
        return RespResult.sucessResult(sysRoleService.delete(sysRoleList));
    }

    /**
     * 分页查询
     * @param pageRequest
     * @return
     */
    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('sys:role:view')")
    @ApiOperation(value = "分页查询", httpMethod = "POST")
    public RespResult findPage(@RequestBody PageRequest pageRequest) {
        return RespResult.sucessResult(sysRoleService.findPage(pageRequest));
    }

    /**
     * 查询全部
     * @return
     */
    @GetMapping("/findAll")
    @PreAuthorize("hasAuthority('sys:role:view')")
    @ApiOperation(value = "查询全部", httpMethod = "GET")
    public RespResult findAll() {
        return RespResult.sucessResult(sysRoleService.findAll());
    }

    /**
     * 查询角色菜单
     * @param roleId
     * @return
     */
    @GetMapping("/findRoleMenus")
    @ApiOperation(value = "查询角色菜单", httpMethod = "GET")
    public RespResult findRoleMenus(@RequestParam Long roleId) {
        return RespResult.sucessResult(sysRoleService.findRoleMenus(roleId));
    }

    /**
     * 保存角色菜单
     * @param roleMenuList
     * @return
     */
    @PostMapping("/saveRoleMenus")
    @ApiOperation(value = "保存角色菜单", httpMethod = "POST")
    public RespResult saveRoleMenus(@RequestBody List<SysRoleMenu> roleMenuList) {
        return RespResult.sucessResult(sysRoleService.saveRoleMenus(roleMenuList));
    }
}
