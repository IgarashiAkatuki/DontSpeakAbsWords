package com.project.service;

import com.project.entity.mysql.User;
import com.project.mapper.UserMapper;
import com.project.service.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Override
    public User queryUser(String username) {
        return userMapper.queryUser(username);
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public int setUserEnable(String username, Boolean flag) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("username",username);
        map.put("flag",flag);

        return userMapper.setUserEnable(map);
    }
}
