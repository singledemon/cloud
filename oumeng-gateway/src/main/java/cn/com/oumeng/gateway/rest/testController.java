package cn.com.oumeng.gateway.rest;

import cn.com.oumeng.common.core.controller.AbstractBaseSpringController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/gate")
public class testController extends AbstractBaseSpringController {

    @GetMapping("/test")
    public Object test(){
        return this.success("ok","222222");
    }
}
