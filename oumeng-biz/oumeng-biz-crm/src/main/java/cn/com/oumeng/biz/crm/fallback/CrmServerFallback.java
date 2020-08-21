package cn.com.oumeng.biz.crm.fallback;

import cn.com.oumeng.biz.crm.feign.CrmServerFeign;
import cn.com.oumeng.common.core.entity.Result;

/*@Component*/
public class CrmServerFallback implements CrmServerFeign {
    /**
     * 失败报错返回无权限
     * @return
     */
    @Override
    public Result getUserById(Long userId) {
       return Result.error();
    }

    @Override
    public String testFeign(Integer id) {
        return null;
    }
}
