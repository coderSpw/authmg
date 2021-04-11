package com.spw.authmg.service.impl;

import com.spw.authmg.core.page.MyPageHelper;
import com.spw.authmg.core.page.PageRequest;
import com.spw.authmg.core.page.PageResult;
import com.spw.authmg.mapper.SysDeptMapper;
import com.spw.authmg.mapper.SysRoleDeptMapper;
import com.spw.authmg.pojo.SysDept;
import com.spw.authmg.pojo.SysDeptExample;
import com.spw.authmg.pojo.SysRoleDeptExample;
import com.spw.authmg.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 机构管理 —— 业务层
 * @author spw
 * @date 2021/02/17
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {
    /**
     * 存放机构删除主键
     */
    private static Set<Long> delIds = new HashSet<>();

    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysRoleDeptMapper sysRoleDeptMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(SysDept sysDept) {
        //根据机构名称查询是否存在
        SysDeptExample sysDeptExample = new SysDeptExample();
        sysDeptExample.createCriteria().andNameEqualTo(sysDept.getName());
        List<SysDept> sysDepts = sysDeptMapper.selectByExample(sysDeptExample);
        if (sysDepts.isEmpty()) {
            //不存在 新增
            return sysDeptMapper.insertSelective(sysDept);
        }
        return sysDeptMapper.updateByPrimaryKeySelective(sysDept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(SysDept sysDept) {
        int count = 0;
        try {
            //1.查询所有机构
            List<SysDept> sysDepts = sysDeptMapper.selectByExample(new SysDeptExample());
            //2.递归获取机构主键
            findDeptDelIds(sysDepts,String.valueOf(sysDept.getId()));
            //3.删除机构
            SysDept dept = new SysDept();
            dept.setDelFlag((byte) 1);
            SysDeptExample sysDeptExample = new SysDeptExample();
            sysDeptExample.createCriteria().andIdIn(new ArrayList<>(delIds));
            count = sysDeptMapper.updateByExampleSelective(dept, sysDeptExample);
            //4.删除角色机构中间表数据
            SysRoleDeptExample sysRoleDeptExample = new SysRoleDeptExample();
            sysRoleDeptExample.createCriteria().andDeptIdIn(new ArrayList<>(delIds));
            sysRoleDeptMapper.deleteByExample(sysRoleDeptExample);
        } catch (Exception e) {
            e.getMessage();
        } finally {
            //5.清空集合
            delIds.clear();
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<SysDept> sysDeptList) {
        int count = 0;
        try {
            //1.获取机构列表
            List<SysDept> sysDepts = sysDeptMapper.selectByExample(new SysDeptExample());
            //2.获取要删除的集合列表
            List<Long> ids = sysDeptList.stream().map(SysDept::getId).collect(Collectors.toList());
            //3.递归获取要删除机构id
            findDeptDelIds(sysDepts, ids.toString());
            //4.根据机构主键列表清除机构 删除当前机构及子机构
            SysDeptExample sysDeptExample = new SysDeptExample();
            sysDeptExample.createCriteria().andIdIn(new ArrayList<>(delIds));
            count = sysDeptMapper.updateByExampleSelective(new SysDept((byte) 1), sysDeptExample);
            //5.删除拥有当前机构角色中间表数据
            SysRoleDeptExample sysRoleDeptExample = new SysRoleDeptExample();
            sysRoleDeptExample.createCriteria().andDeptIdIn(new ArrayList<>(delIds));
            sysRoleDeptMapper.deleteByExample(sysRoleDeptExample);
        } catch (Exception e) {
            e.getMessage();
        } finally {
            delIds.clear();
        }
        return 0;
    }

    /**
     * 递归获取需要删除的机构id
     * @param sysDepts 机构列表
     * @param parentId 父id
     * @return
     */
    private void findDeptDelIds(List<SysDept> sysDepts, String parentId) {
        sysDepts.stream()
                .filter(x -> !StringUtils.isEmpty(x.getId()) && parentId.contains(String.valueOf(x.getId())))
                .forEach(x -> {
                    delIds.add(x.getId());
                    findDeptDelIds(sysDepts,String.valueOf(x.getId()));
                });
    }

    @Override
    public SysDept findById(Long id) {
        return sysDeptMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysDept> findAll() {
        return sysDeptMapper.selectByExample(new SysDeptExample());
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MyPageHelper.findPage(pageRequest,sysDeptMapper,"selectByExample", new SysDeptExample());
    }

    @Override
    public List<SysDept> findTree() {
        //1.查询所有机构列表
        List<SysDept> sysDeptList = this.findAll();
        //2.获取机构数根节点
        List<SysDept> result = new ArrayList<>();
        sysDeptList.stream()
                .filter(x -> StringUtils.isEmpty(x.getParentId()) || x.getParentId() == 0L)
                .forEach(x -> {
                    x.setLevel(0);
                    result.add(x);
                });
        //2.递归查询机构树
        findChildren(result, sysDeptList);
        return result;
    }

    /**
     * 递归查询机构树
     * @param result 结果集
     * @param sysDeptList 每层树集合
     */
    private void findChildren(List<SysDept> result, List<SysDept> sysDeptList) {
        result.forEach(x -> {
            List<SysDept> children = sysDeptList.stream()
                    .filter(y -> !StringUtils.isEmpty(x.getId()) && x.getId().equals(y.getParentId()))
                    .peek(y -> y.setLevel(x.getLevel() + 1))
                    .collect(Collectors.toList());
            x.setChildren(children);
            findChildren(children,sysDeptList);
        });

    }
}
