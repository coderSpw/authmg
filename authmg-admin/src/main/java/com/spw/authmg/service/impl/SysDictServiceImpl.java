package com.spw.authmg.service.impl;

import com.spw.authmg.core.page.MyPageHelper;
import com.spw.authmg.core.page.PageRequest;
import com.spw.authmg.core.page.PageResult;
import com.spw.authmg.mapper.SysDictMapper;
import com.spw.authmg.pojo.SysDict;
import com.spw.authmg.pojo.SysDictExample;
import com.spw.authmg.service.SysDictService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysDictServiceImpl implements SysDictService {
    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        Object label = pageRequest.getParam("label");
        if (label != null){
            return MyPageHelper.findPage(pageRequest,sysDictMapper,"findPageByLabel",label);
        }
        return MyPageHelper.findPage(pageRequest,sysDictMapper);
    }

    @Override
    public List<SysDict> findByLabel(@Param(value = "label") String label) {
        SysDictExample query = new SysDictExample();
        query.createCriteria().andLabelLike("%" + label + "%");
        return sysDictMapper.selectByExample(query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(SysDict sysDict) {
        // 获取id 查询表中是否存在数据不存在新增 存在更新
        SysDict sysDict1 = sysDictMapper.selectByPrimaryKey(sysDict.getId());
        if (sysDict1 == null){
            // 新增
            return sysDictMapper.insert(sysDict);
        }
        //更新
        return sysDictMapper.updateByPrimaryKey(sysDict);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<SysDict> sysDictlist) {
        List<Long> ids = new ArrayList<>();
        for (SysDict sysDict : sysDictlist) {
            ids.add(sysDict.getId());
        }
        SysDictExample query = new SysDictExample();
        query.createCriteria().andIdIn(ids);
        return sysDictMapper.deleteByExample(query);
    }
}
