package com.spw.authmg.backup.service;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description 数据脚本备份
 * @Author spw
 * @Date 2021/4/5
 */
public interface BackUpService {
    /**
     * 数据库备份
     * @param database
     * @return
     */
    Boolean backUp(String database, Boolean isDefault);

    /**
     * 数据库还原
     * @return
     */
    Boolean restore(String database);

    /**
     * 查询全部备份文件
     * @return
     */
    List<String> findByName(String name);

    /**
     * 根据名称删除文件
     * @param name
     * @return
     */
    void deleteByName(@NotNull String name);
}
