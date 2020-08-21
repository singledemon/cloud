package cn.com.oumeng.file.feign;


import cn.com.oumeng.common.core.entity.Result;
import cn.com.oumeng.file.fallback.CrmServerFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "oumeng-crm",fallback = CrmServerFallback.class)
public interface ICrmServerFeign {
    /**
     * 调用crm服务，获取user对象
     * @return
     */
    @RequestMapping("/api/rpc/testController/getUserById")
    public Result getUserById(@RequestParam("userId") Long userId);
}
