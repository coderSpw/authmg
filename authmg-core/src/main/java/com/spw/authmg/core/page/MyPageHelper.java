package com.spw.authmg.core.page;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spw.authmg.security.utils.ReflectionUtils;

import java.util.List;

/**
 * 分页助手
 * @author spw
 * @date 2021/02/15
 */
public class MyPageHelper {
    private static final String FIND_PAGE = "findPage";


    /**
     * 分页
     * @param pageRequest 分页请求
     * @param mapper  mapper接口
     * @return
     */
    public static PageResult findPage(PageRequest pageRequest, Object mapper) {
        return findPage(pageRequest, mapper, FIND_PAGE);
    }

    /**
     * 分页
     * @param pageRequest 分页请求
     * @param mapper   mapper接口
     * @param methodName  方法名称
     * @param args    请求参数
     * @return
     */
    @SuppressWarnings("unchecked")
    public static PageResult findPage(PageRequest pageRequest, Object mapper
            , String methodName, Object... args) {
        //设置分页参数
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum,pageSize);
        //利用反射执行方法
        Object result = ReflectionUtils.invoke(mapper, methodName, args);
        return getPageResult(new PageInfo((List) result));
    }

    /**
     * 分页信息封装
     * @param pageInfo
     * @return
     */
    private static PageResult getPageResult(PageInfo<?> pageInfo) {
        return new PageResult(pageInfo.getPageNum(), pageInfo.getPageSize()
                            , pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }
}
