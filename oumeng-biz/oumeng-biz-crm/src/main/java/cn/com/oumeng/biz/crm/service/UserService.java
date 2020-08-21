package cn.com.oumeng.biz.crm.service;


import cn.com.oumeng.biz.crm.mapper.UserMapper;
import cn.com.oumeng.biz.crm.model.User;
import cn.com.oumeng.common.core.service.impl.AbstractServiceImpl;
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
