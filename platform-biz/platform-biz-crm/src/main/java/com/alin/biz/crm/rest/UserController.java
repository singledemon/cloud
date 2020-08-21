package com.alin.biz.crm.rest;

import com.alin.biz.crm.model.User;
import com.alin.biz.crm.service.UserService;
import com.alin.common.core.controller.AbstractSpringController;
import com.alin.common.core.entity.Result;
import com.alin.common.log.annotation.Log;
import com.alin.common.log.enums.BusinessType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractSpringController<User, UserService> {


    @PostMapping("/testController")
    public Result testController(){
        User user = this.baseService.getById(1);

        List<User> userList = this.baseService.getUserList();

        System.out.println(userList+"==");
        return Result.ok(200,"测试",user);
    }

    @PostMapping("/addUser")
    @Log(title = "操作日志", businessType = BusinessType.INSERT)
    public Object addUser(@RequestBody User user){
        boolean save = baseService.save(user);

        return success("ok",user);
    }
}
