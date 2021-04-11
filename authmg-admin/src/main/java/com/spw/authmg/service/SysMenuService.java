package com.spw.authmg.service;

import com.spw.authmg.core.service.CrudService;
import com.spw.authmg.pojo.SysMenu;

import java.util.List;

public interface SysMenuService extends CrudService<SysMenu> {
    /**
     * 查询导航菜单
     * @param userName
     * @return
     */
    List<SysMenu> findNavTree(String userName);

    /**
     * 查询菜单树
     * @return
     */
    List<SysMenu> findMenuTree();

    /**
     * 根据用户名查询菜单
     * @param name
     * @return
     */
    List<SysMenu> findByUserName(String name);
}
