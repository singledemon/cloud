package cn.com.oumeng.biz.crm.rest;

import cn.com.oumeng.biz.crm.model.User;
import cn.com.oumeng.biz.crm.service.UserService;
import cn.com.oumeng.common.core.controller.AbstractSpringController;
import cn.com.oumeng.common.core.entity.Result;
import cn.com.oumeng.common.log.annotation.Log;
import cn.com.oumeng.common.log.enums.BusinessType;
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
