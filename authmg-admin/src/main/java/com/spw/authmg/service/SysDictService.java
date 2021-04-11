package com.spw.authmg.service;


import com.spw.authmg.core.page.PageRequest;
import com.spw.authmg.core.page.PageResult;
import com.spw.authmg.pojo.SysDict;

import java.util.List;

public interface SysDictService {

    /**
     * 分页查询
     */
    PageResult findPage(PageRequest pageRequest);

    /**
     * 根据标签模糊查询
     */
    List<SysDict> findByLabel(String label);

    /**
     * 添加数据
     */
    int save(SysDict sysDict);

    /**
     * 删除指定数据
     */
    int delete(List<SysDict> sysDictlist);
}
