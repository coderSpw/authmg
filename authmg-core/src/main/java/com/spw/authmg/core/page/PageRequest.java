package com.spw.authmg.core.page;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 分页请求
 * @author spw
 * @date 2021/02/15
 */
@Data
public class PageRequest {

    /**
     * 当前页码
     */
    @Min(value = 1, message = "起始页面最小为1")
    private int pageNum = 1;

    /**
     * 每页大小
     */
    private int pageSize = 10;

    /**
     * 请求参数
     */
    private Map<String,Object> params = new HashMap<>();

    public Object getParam(String key) {
        return params.get(key);
    }
}
