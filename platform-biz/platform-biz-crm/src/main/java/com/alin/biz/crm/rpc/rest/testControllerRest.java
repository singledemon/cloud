package com.alin.biz.crm.rpc.rest;

import com.alin.biz.crm.model.User;
import com.alin.biz.crm.service.UserService;
import com.alin.common.core.controller.AbstractBaseSpringController;
import com.alin.common.core.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rpc/testController")
@Transactional(rollbackFor = Exception.class)
public class testControllerRest extends AbstractBaseSpringController {

    @Autowired
    private UserService userService;

    /**
     * getUserById
     * @return
     */
    @RequestMapping("/getUserById")
    public Result getUserById(){
        User user = userService.getById(1);
        return  Result.ok(200,"成功",user);
    }


}
