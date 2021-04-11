package com.spw.authmg.service.impl;

import com.spw.authmg.constant.Constants;
import com.spw.authmg.core.page.MyPageHelper;
import com.spw.authmg.core.page.PageRequest;
import com.spw.authmg.core.page.PageResult;
import com.spw.authmg.mapper.*;
import com.spw.authmg.pojo.*;
import com.spw.authmg.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysRoleDeptMapper sysRoleDeptMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(SysRole record) {
        SysRole sysRole = this.findById(record.getId());
        if (sysRole == null) {
            //新增
            sysRoleMapper.insertSelective(record);
        }
        //修改
        return sysRoleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(SysRole record) {
        //删除角色
        record.setDelFlag((byte) 1);
        int count = sysRoleMapper.updateByPrimaryKeySelective(record);
        //清除用户角色表数据
        SysUserRoleExample userRoleExample = new SysUserRoleExample();
        userRoleExample.createCriteria().andRoleIdEqualTo(record.getId());
        sysUserRoleMapper.deleteByExample(userRoleExample);
        //清除角色菜单表数据
        SysRoleMenuExample roleMenuExample = new SysRoleMenuExample();
        roleMenuExample.createCriteria().andRoleIdEqualTo(record.getId());
        sysRoleMenuMapper.deleteByExample(roleMenuExample);
        //清除角色机构表数据
        SysRoleDeptExample roleDeptExample = new SysRoleDeptExample();
        roleDeptExample.createCriteria().andRoleIdEqualTo(record.getId());
        sysRoleDeptMapper.deleteByExample(roleDeptExample);
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<SysRole> sysRoleList) {
        //获取主键集合 排除超级管理员
        List<Long> ids = sysRoleList.stream()
                .filter(x -> !Constants.ADMIN.equalsIgnoreCase(x.getName()))
                .map(SysRole::getId)
                .collect(Collectors.toList());
        //批量删除角色
        SysRole sysRole = new SysRole((byte) 1);
        SysRoleExample roleExample = new SysRoleExample();
        roleExample.createCriteria().andIdIn(ids);
        sysRoleMapper.updateByExampleSelective(sysRole, roleExample);
        //清除用户角色表数据
        SysUserRoleExample userRoleExample = new SysUserRoleExample();
        userRoleExample.createCriteria().andRoleIdIn(ids);
        sysUserRoleMapper.deleteByExample(userRoleExample);
        //清除角色菜单表数据
        SysRoleMenuExample roleMenuExample = new SysRoleMenuExample();
        roleMenuExample.createCriteria().andRoleIdIn(ids);
        sysRoleMenuMapper.deleteByExample(roleMenuExample);
        //清除角色机构表数据
        SysRoleDeptExample roleDeptExample = new SysRoleDeptExample();
        roleDeptExample.createCriteria().andRoleIdIn(ids);
        sysRoleDeptMapper.deleteByExample(roleDeptExample);
        return 0;
    }

    @Override
    public SysRole findById(Long id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysRole> findAll() {
        return sysRoleMapper.selectByExample(new SysRoleExample());
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        SysRoleExample roleExample = new SysRoleExample();
        roleExample.createCriteria().andNameEqualTo((String) pageRequest.getParam("name"));
        return MyPageHelper.findPage(pageRequest, sysRoleMapper, "selectByExample", roleExample);
    }

    @Override
    public List<SysMenu> findRoleMenus(Long roleId) {
        //根据主键查询角色
        SysRole role = sysRoleMapper.selectByPrimaryKey(roleId);
        //判断该角色是否为超级管理员  超级管理员展示全部菜单
        if(Constants.ADMIN.equalsIgnoreCase(role.getName())) {
            return sysMenuMapper.selectByExample(new SysMenuExample());
        }
        return sysMenuMapper.findRoleMenus(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveRoleMenus(List<SysRoleMenu> roleMenuList) {
        if (roleMenuList == null || roleMenuList.isEmpty()) {
            return 0;
        }
        //获取roleid集合
        List<Long> roleIds = roleMenuList.stream().map(SysRoleMenu::getRoleId).collect(Collectors.toList());
        //清除中间数据
        SysRoleMenuExample roleMenuExample = new SysRoleMenuExample();
        roleMenuExample.createCriteria().andRoleIdIn(roleIds);
        sysRoleMenuMapper.deleteByExample(roleMenuExample);
        //新增数据
        roleMenuList.forEach(x -> sysRoleMenuMapper.insert(x));
        return 1;
    }
}
