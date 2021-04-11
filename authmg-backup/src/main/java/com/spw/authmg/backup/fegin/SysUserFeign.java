package com.spw.authmg.backup.fegin;

import com.spw.authmg.core.http.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 用户管理
 * @Author spw
 * @Date 2021/4/11
 */
@FeignClient(value = "authmg-admin", fallback = SysUserFallback.class)
public interface SysUserFeign {

    /**
     * 查询全部用户
     * @return
     */
    @RequestMapping("/user/findAll")
    RespResult findAll();
}
