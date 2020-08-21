package cn.com.oumeng.file.fallback;

import cn.com.oumeng.common.core.entity.Result;
import cn.com.oumeng.file.feign.ICrmServerFeign;
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
