package com.spw.authmg.service;

import com.spw.authmg.core.service.CrudService;
import com.spw.authmg.pojo.SysConfig;

import java.util.List;

public interface SysConfigService extends CrudService<SysConfig> {
    /**
     * 根据标签名查询
     */
    List<SysConfig> findByLabel(String label);
}
