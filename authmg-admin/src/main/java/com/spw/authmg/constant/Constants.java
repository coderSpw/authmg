package com.spw.authmg.constant;

/**
 * 常量池
 * @author spw
 * @date 2021/02/17
 */
public interface Constants {
    /**
     * 超级管理员
     */
    String ADMIN="admin";

    /**
     * 数据源常量
     */
    final class DatasourceType {
        public static final String MASTER = "master";
        public static final String SLAVE = "slave";
    }

    /**
     * 方法名称前缀匹配规则
     */
    final class MethodRule {
        public static final String INSERT = "insert";
        public static final String SAVE = "save";
        public static final String UPDATE = "update";
        public static final String DELETE = "delete";
        public static final String FIND = "find";
        public static final String QUERY = "query";
        public static final String COUNT = "count";
        public static final String SELECT = "select";
    }

}
