package com.spw.authmg.service;

import com.spw.authmg.core.service.CrudService;
import com.spw.authmg.pojo.SysDept;

import java.util.List;

public interface SysDeptService extends CrudService<SysDept> {
    /**
     * 查询机构树
     * @return
     */
    List<SysDept> findTree();
}
