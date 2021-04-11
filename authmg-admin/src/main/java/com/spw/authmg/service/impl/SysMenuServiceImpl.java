package com.spw.authmg.service.impl;

import com.spw.authmg.constant.Constants;
import com.spw.authmg.core.page.MyPageHelper;
import com.spw.authmg.core.page.PageRequest;
import com.spw.authmg.core.page.PageResult;
import com.spw.authmg.mapper.SysMenuMapper;
import com.spw.authmg.mapper.SysRoleMenuMapper;
import com.spw.authmg.pojo.SysMenu;
import com.spw.authmg.pojo.SysMenuExample;
import com.spw.authmg.pojo.SysRoleMenuExample;
import com.spw.authmg.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(SysMenu record) {
        if (record == null || StringUtils.isEmpty(record.getId())) {
            return 0;
        }
        SysMenu menu = this.findById(record.getId());
        if (menu == null) {
            return sysMenuMapper.insert(record);
        }
        return sysMenuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(SysMenu record) {
        //删除菜单
        record.setDelFlag((byte) 1);
        sysMenuMapper.updateByPrimaryKeySelective(record);
        //清除菜单角色中间表数据
        SysRoleMenuExample roleMenuExample = new SysRoleMenuExample();
        roleMenuExample.createCriteria().andMenuIdEqualTo(record.getId());
        sysRoleMenuMapper.deleteByExample(roleMenuExample);
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<SysMenu> menuList) {
        //获取删除菜单主键id集合
        List<Long> ids = menuList.stream().map(SysMenu::getId).collect(Collectors.toList());
        //删除菜单
        SysMenu menu = new SysMenu((byte) 1);
        SysMenuExample menuExample = new SysMenuExample();
        menuExample.createCriteria().andIdIn(ids);
        int count = sysMenuMapper.updateByExampleSelective(menu, menuExample);
        //清除角色菜单中间表数据
        SysRoleMenuExample roleMenuExample = new SysRoleMenuExample();
        roleMenuExample.createCriteria().andMenuIdIn(ids);
        sysRoleMenuMapper.deleteByExample(roleMenuExample);
        return count;
    }

    @Override
    public SysMenu findById(Long id) {
        return sysMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysMenu> findAll() {
        return sysMenuMapper.selectByExample(new SysMenuExample());
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MyPageHelper.findPage(pageRequest, sysMenuMapper, "selectByExample", new SysMenuExample());
    }

    @Override
    public List<SysMenu> findNavTree(String userName) {
        return null;
    }

    @Override
    public List<SysMenu> findMenuTree() {
        //查询所有菜单
        List<SysMenu> menuList = this.findAll();
        List<SysMenu> result = new ArrayList<>();
        menuList.stream()
                .filter(x -> StringUtils.isEmpty(x.getParentId()) || x.getParentId() == 0)
                .forEach(x -> {
                    x.setLevel(1);
                    //根节点
                    result.add(x);
                });
        findMenuChildren(result, menuList);
        return result;
    }

    @Override
    public List<SysMenu> findByUserName(String name) {
        if (StringUtils.isEmpty(name) || Constants.ADMIN.equalsIgnoreCase(name)) {
            //超级权限查询所有菜单
            return sysMenuMapper.selectByExample(new SysMenuExample());
        }
        return sysMenuMapper.findByUsername(name);
    }

    private void findMenuChildren(List<SysMenu> result, List<SysMenu> menuList) {
        result.forEach(x -> {
            //存放子节点的集合
            List<SysMenu> children = menuList.stream()
                    .filter(y -> !StringUtils.isEmpty(x.getId()) && x.getId().equals(y.getParentId()))
                    .peek(y -> y.setLevel(x.getLevel() + 1))
                    .collect(Collectors.toList());
            x.setChildren(children);
            //递归存值
            findMenuChildren(children, menuList);
        });
    }
}
