package com.spw.authmg.configuration.datasource;

import com.spw.authmg.constant.Constants;

/**
 * @Description 数据源上下文
 * @Author spw
 * @Date 2021/3/28
 */
public final class DatasourceContextHolder {
    /**
     * 设置线程局部变量，保证线程之间互相独立
     */
    private static final  ThreadLocal<String> holder = new ThreadLocal<>();

    static {
        //首次加载设置master为默认数据源
        holder.set(Constants.DatasourceType.MASTER);
    }
    /**
     * 获取数据源
     * @return
     */
    public static String getDatasource() {
        return holder.get();
    }

    /**
     * 设置数据源
     * @param name
     */
    public static void setDatasource(String name) {
        holder.set(name);
    }
}
