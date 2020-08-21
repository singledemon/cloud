package cn.com.oumeng.file.rest;

import cn.com.oumeng.common.core.controller.AbstractSpringController;
import cn.com.oumeng.common.core.entity.Result;
import cn.com.oumeng.file.feign.ICrmServerFeign;
import cn.com.oumeng.file.model.SystemFile;
import cn.com.oumeng.file.service.SystemFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/file")
public class SystemFileController extends AbstractSpringController<SystemFile, SystemFileService> {

    @Autowired
    private ICrmServerFeign crmServerFeign;

    @PostMapping("/testController")
    public Result testController(){

        Result res = crmServerFeign.getUserById(Long.parseLong("1"));
        return res;
    }

}
