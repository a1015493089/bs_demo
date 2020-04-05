package com.example.demobs.service;

import com.example.demobs.mapper.UserMapper;
import com.example.demobs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void creatOrUpdate(User user) {
       User dbUser= userMapper.findByAccountID(user.getAccountId());
       if(dbUser==null){
           //无该accountid用户 所以插入数据
           user.setGmtCreat(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreat());
           userMapper.insert(user);
       }
       else {
           //数据更新
           dbUser.setGmtModified(System.currentTimeMillis());
           dbUser.setAvatarUrl(user.getAvatarUrl());
           dbUser.setName(user.getName());
           dbUser.setToken(user.getToken());
           userMapper.update(dbUser);
       }
    }
}
