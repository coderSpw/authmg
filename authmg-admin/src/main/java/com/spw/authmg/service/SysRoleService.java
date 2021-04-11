package com.spw.authmg.service;


import com.spw.authmg.core.service.CrudService;
import com.spw.authmg.pojo.SysMenu;
import com.spw.authmg.pojo.SysRole;
import com.spw.authmg.pojo.SysRoleMenu;

import java.util.List;

public interface SysRoleService extends CrudService<SysRole> {
    /**
     * 查询角色菜单
     * @param roleId
     * @return
     */
    List<SysMenu> findRoleMenus(Long roleId);

    /**
     * 保存角色菜单
     * @param sysRoleMenu
     * @return
     */
    int saveRoleMenus(List<SysRoleMenu> sysRoleMenu);
}
