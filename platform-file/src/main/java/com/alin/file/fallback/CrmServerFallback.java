package com.alin.file.fallback;

import com.alin.common.core.entity.Result;
import com.alin.file.feign.ICrmServerFeign;
import org.springframework.stereotype.Component;

@Component
public class CrmServerFallback implements ICrmServerFeign {
    /**
     * 失败报错返回无权限
     * @return
     */
    @Override
    public Result getUserById(Long userId) {
       return Result.error();
    }
}
