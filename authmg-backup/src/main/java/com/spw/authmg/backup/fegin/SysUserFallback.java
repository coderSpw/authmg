package com.spw.authmg.backup.fegin;

import com.spw.authmg.core.http.RespResult;
import org.springframework.stereotype.Component;

/**
 * @Description 用户管理服务调用（兜底）
 * @Author spw
 * @Date 2021/4/11
 */
@Component
public class SysUserFallback implements SysUserFeign {

    @Override
    public RespResult findAll() {
        return RespResult.failResult("操作繁忙,稍后再重试~~~~");
    }
}
