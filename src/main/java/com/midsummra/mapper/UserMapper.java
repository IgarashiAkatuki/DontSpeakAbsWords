package com.midsummra.mapper;

import com.midsummra.pojo.User;

import java.util.Date;
import java.util.Map;

public interface UserMapper {
     User getUser(String username);
     int addUser(User user);
     int refreshDate(Map map);
}
