package com.spw.authmg.service;

import com.spw.authmg.core.page.PageRequest;
import com.spw.authmg.core.page.PageResult;
import com.spw.authmg.core.service.CrudService;
import com.spw.authmg.pojo.SysUser;
import com.spw.authmg.pojo.SysUserRole;

import java.util.List;
import java.util.Set;

public interface SysUserService extends CrudService<SysUser> {
    /**
     * 查询所有用户
     * @return
     */
    @Override
    List<SysUser> findAll();

    /**
     * 分页查询用户
     * @param pageRequest
     * @return
     */
    @Override
    PageResult findPage(PageRequest pageRequest);

    /**
     * 根据名称查询
     * @param name
     * @return
     */
    SysUser findByName(String name);

    /**
     * 查询用户角色
     * @param id
     * @return
     */
    List<SysUserRole> findUserRoles(Long id);

    /**
     * 获取用户权限列表
     * @param name
     * @return
     */
    Set<String> findPermissions(String name);
}
