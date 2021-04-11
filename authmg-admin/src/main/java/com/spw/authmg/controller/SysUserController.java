package com.spw.authmg.controller;

import com.spw.authmg.constant.Constants;
import com.spw.authmg.core.page.PageRequest;
import com.spw.authmg.core.http.RespResult;
import com.spw.authmg.pojo.SysUser;
import com.spw.authmg.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  用户管理
 *  @author spw
 *  @date 2021/02/15
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 保存用户
     * @param sysUser
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:user:add') and hasAuthority('sys:user:edit')")
    @ApiOperation(value = "保存用户",httpMethod = "POST")
    public RespResult save(@RequestBody @Validated SysUser sysUser) {
        //根据用户id查询用户
        SysUser user = sysUserService.findById(sysUser.getId());
        if(user != null) {
            if (Constants.ADMIN.equalsIgnoreCase(user.getName())) {
                return RespResult.failResult("超级管理员无法修改!");
            }
        }
        return RespResult.sucessResult(sysUserService.save(sysUser));
    }

    /**
     * 删除用户
     * @param sysUser
     * @return
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @ApiOperation(value = "删除用户", httpMethod = "DELETE")
    public RespResult delete(@RequestBody List<SysUser> sysUser) {
        return RespResult.sucessResult(sysUserService.delete(sysUser));
    }

    /**
     * 查询用户列表
     * @return
     */
    @GetMapping("/findAll")
    @PreAuthorize("hasAuthority('sys:user:view')")
    @ApiOperation(value = "查询列表", notes = "查询所有用户", httpMethod = "GET")
    public RespResult findAll() {
        return RespResult.sucessResult(sysUserService.findAll());
    }

    /**
     * 分页查询用户列表
     * @param pageRequest
     * @return
     */
    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('sys:user:view')")
    @ApiOperation(value = "分页查询", notes = "分页查询用户", httpMethod = "POST")
    public RespResult findPage(@Validated @RequestBody PageRequest pageRequest) {
        return RespResult.sucessResult(sysUserService.findPage(pageRequest));
    }

    /**
     * 根据用户名称查询用户
     * @param name
     * @return
     */
    @PostMapping("/findByName")
    @PreAuthorize("hasAuthority('sys:user:view')")
    @ApiOperation(value = "根据名称查询",httpMethod = "POST")
    public RespResult findByName(@NotNull @RequestParam String name) {
        return RespResult.sucessResult(sysUserService.findByName(name));
    }

    /**
     * 查询用户角色
     * @param id
     * @return
     */
    @GetMapping("/findUserRoles/{id}")
    @PreAuthorize("hasAuthority('sys:user:view')")
    @ApiOperation(value = "查询用户角色",httpMethod = "GET")
    public RespResult findUserRoles(@NotNull @PathVariable Long id) {
        return RespResult.sucessResult(sysUserService.findUserRoles(id));
    }

}
