package com.alin.biz.crm.feign;


import com.alin.biz.crm.fallback.CrmServerFallback;
import com.alin.common.core.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "oumeng-crm",fallback = CrmServerFallback.class)
public interface CrmServerFeign {
    /**
     * 调用签权服务，判断用户是否有权限
     * @return
     */
    @RequestMapping("/user/getUserById")
    public Result getUserById(@RequestParam("userId") Long userId );

    @GetMapping("/test/feign/{id}")
    String testFeign(@RequestParam("id") Integer id);
}
