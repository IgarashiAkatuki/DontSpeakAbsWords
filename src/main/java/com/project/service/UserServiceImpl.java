package com.project.service;

import com.project.mapper.UserMapper;
import com.project.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Override
    public User getUser(String username) {
        return userMapper.getUser(username);
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public int refreshDate(String username, Date date) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username",username);
        map.put("date",date);
        return userMapper.refreshDate(map);
    }
}
