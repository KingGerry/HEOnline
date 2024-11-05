package com.hy.business.services.impl;

import com.hy.business.backentity.BackUser;
import com.hy.business.backentity.Role;
import com.hy.business.dao.UserMapper;
import com.hy.business.entity.User;
import com.hy.business.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public BackUser Login(User user) {
        BackUser bu = userMapper.Login(user);
        for (Role role : bu.getRoles()) {
            role.setBags(userMapper.GetBages(role.getRoleId(),1));
            role.setZhuangBeis(userMapper.GetBages(role.getRoleId(),0));
            role.setCompletedTasks(userMapper.GetTasks(role.getRoleId()));
        }
        return bu;
    }
}
