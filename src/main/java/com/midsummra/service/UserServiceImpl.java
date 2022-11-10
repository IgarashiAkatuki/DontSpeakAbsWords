package com.midsummra.service;

import com.midsummra.mapper.UserMapper;
import com.midsummra.pojo.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService{

    private UserMapper userMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

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
