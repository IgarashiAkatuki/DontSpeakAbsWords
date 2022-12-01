package com.project.service;

import com.project.pojo.User;

import java.util.Date;

public interface UserService {
    public User getUser(String username);
    public int addUser(User user);
    public int refreshDate(String username, Date date);
}
