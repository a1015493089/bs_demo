package com.example.demobs.service;

import com.example.demobs.mapper.UserMapper;
import com.example.demobs.model.User;
import com.example.demobs.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void creatOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
       if(users.size()==0){
           //无该accountid用户 所以插入数据
           user.setGmtCreat(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreat());
           userMapper.insert(user);
       }
       else {
           //数据更新
           User dbUser=users.get(0);
           User updateUser=new User();
           updateUser.setGmtModified(System.currentTimeMillis());
           updateUser.setAvatarUrl(user.getAvatarUrl());
           updateUser.setName(user.getName());
           updateUser.setToken(user.getToken());
           UserExample example=new UserExample();
           userExample.createCriteria().andIdEqualTo(dbUser.getId());
           userMapper.updateByExampleSelective(updateUser,example);
       }
    }
}
