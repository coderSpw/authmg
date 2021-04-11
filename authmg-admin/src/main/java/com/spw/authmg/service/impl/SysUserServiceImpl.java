package com.spw.authmg.service.impl;

import com.spw.authmg.constant.Constants;
import com.spw.authmg.core.page.MyPageHelper;
import com.spw.authmg.core.page.PageRequest;
import com.spw.authmg.core.page.PageResult;
import com.spw.authmg.mapper.SysUserMapper;
import com.spw.authmg.mapper.SysUserRoleMapper;
import com.spw.authmg.pojo.*;
import com.spw.authmg.security.utils.PasswordUtils;
import com.spw.authmg.service.SysMenuService;
import com.spw.authmg.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户管理 —— 业务层
 * @author spw
 * @date 2021/02/15
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(SysUser sysUser) {
        //先查询 判断库中是否存在 存在更新 不存在新增
        SysUser user = sysUserMapper.selectByPrimaryKey(sysUser.getId());
        //密码加盐加密
        String salt = PasswordUtils.getSalt();
        sysUser.setPassword(PasswordUtils.encode(sysUser.getPassword(), salt));
        sysUser.setSalt(salt);
        if (user == null) {
            return sysUserMapper.insert(sysUser);
        } else {
            return sysUserMapper.updateByPrimaryKey(sysUser);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(SysUser record) {
        return sysUserMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<SysUser> sysUsers) {
        //获取id集合  排除超级管理员
        List<Long> ids = sysUsers.stream()
                                .filter(x-> !(x.getName().equalsIgnoreCase(Constants.ADMIN)))
                                .map(SysUser::getId)
                                .collect(Collectors.toList());
        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.createCriteria().andIdIn(ids);
        //根据主键id批量删除用户
        int count = sysUserMapper.deleteByExample(sysUserExample);
        //删除用户角色
        SysUserRoleExample userRoleExample = new SysUserRoleExample();
        userRoleExample.createCriteria().andUserIdIn(ids);
        sysUserRoleMapper.deleteByExample(userRoleExample);
        return count;
    }

    @Override
    public SysUser findById(Long id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysUser> findAll() {
        return sysUserMapper.selectByExample(new SysUserExample());
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MyPageHelper.findPage(pageRequest, sysUserMapper, "selectByExample", new SysUserExample());
    }

    @Override
    public SysUser findByName(String name) {
        SysUserExample example = new SysUserExample();
        example.createCriteria().andNameLike(name);
        List<SysUser> userList = sysUserMapper.selectByExample(example);
        return userList.size() > 0 ? userList.get(0) : null;
    }

    @Override
    public List<SysUserRole> findUserRoles(Long id) {
        SysUserRoleExample example = new SysUserRoleExample();
        example.createCriteria().andUserIdEqualTo(id);
        return sysUserRoleMapper.selectByExample(example);
    }

    @Override
    public Set<String> findPermissions(String name) {
        Set<String> permissions = new HashSet<>();
        //根据用户名查询菜单
        List<SysMenu> menuList = sysMenuService.findByUserName(name);
        menuList.stream().forEach(o -> {
            permissions.add(o.getPerms());
        });
        return permissions;
    }
}
