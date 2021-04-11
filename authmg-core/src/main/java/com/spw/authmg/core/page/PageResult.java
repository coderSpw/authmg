package com.spw.authmg.core.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果
 * @author spw
 * @date 2021/02/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult {
    /**
     * 页码
     */
    private int pageNum;
    /**
     * 每页大小
     */
    private int pageSize;
    /**
     * 总条数
     */
    private long totalSize;
    /**
     * 总页数
     */
    private int totalPages;
    /**
     * 分页数据
     */
    private List<?> content;
}
