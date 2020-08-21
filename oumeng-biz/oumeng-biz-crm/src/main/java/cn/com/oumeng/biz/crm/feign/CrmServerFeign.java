package cn.com.oumeng.biz.crm.feign;


import cn.com.oumeng.biz.crm.fallback.CrmServerFallback;
import cn.com.oumeng.common.core.entity.Result;
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
