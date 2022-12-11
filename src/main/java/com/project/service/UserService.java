package com.project.service;

import com.project.entity.User;

import java.util.Map;

public interface UserService {

    // 获取用户
    User queryUser(String username);

    // 添加用户
    int addUser(User user);

    // 设置用户可用状态
    // map中的值为: username,flag
    int setUserEnable(String username, Boolean flag);
}
