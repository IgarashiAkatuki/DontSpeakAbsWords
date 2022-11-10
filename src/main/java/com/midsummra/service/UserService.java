package com.midsummra.service;

import com.midsummra.pojo.User;

import java.util.Date;
import java.util.Map;

public interface UserService {
    public User getUser(String username);
    public int addUser(User user);
    public int refreshDate(String username, Date date);
}
