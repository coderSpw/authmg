package com.spw.authmg.core.service;

import com.spw.authmg.core.page.PageRequest;
import com.spw.authmg.core.page.PageResult;

import java.util.List;

/**
 *  CRUD通用接口
 *  @author spw
 *  @date 2021/02/15
 *  @param <T>
 */
public interface CrudService<T> {

    /**
     * 新增
     * @param record
     */
    public int save(T record);

    /**
     * 删除
     * @param record
     */
    public int delete(T record);

    /**
     * 批量删除
     * @param records
     * @return
     */
    public int delete(List<T> records);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public T findById(Long id);

    /**
     * 查询所有
     * @return
     */
    public List<T> findAll();


    /**
     * 分页查询
     * @param pageRequest
     * @return
     */
    public PageResult findPage(PageRequest pageRequest);

}
