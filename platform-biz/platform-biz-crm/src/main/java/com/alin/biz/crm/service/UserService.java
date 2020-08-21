package com.alin.biz.crm.service;


import com.alin.biz.crm.mapper.UserMapper;
import com.alin.biz.crm.model.User;
import com.alin.common.core.service.impl.AbstractServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService extends AbstractServiceImpl<UserMapper, User> {

    public List<User> getUserList(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        List<User> users = baseMapper.selectList(wrapper);
        return users;
    }
}
