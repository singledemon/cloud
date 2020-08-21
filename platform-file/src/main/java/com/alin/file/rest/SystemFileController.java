package com.alin.file.rest;

import com.alin.common.core.controller.AbstractSpringController;
import com.alin.common.core.entity.Result;
import com.alin.file.feign.ICrmServerFeign;
import com.alin.file.model.SystemFile;
import com.alin.file.service.SystemFileService;
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
