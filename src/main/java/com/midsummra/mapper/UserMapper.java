package com.midsummra.mapper;

import com.midsummra.pojo.User;

import java.util.Date;
import java.util.Map;

public interface UserMapper {
    public User getUser(String username);
    public int addUser(User user);
    public int refreshDate(Map map);
}
