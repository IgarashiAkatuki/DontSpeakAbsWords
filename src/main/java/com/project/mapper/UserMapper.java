package com.project.mapper;

import com.project.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface UserMapper {
    User getUser(String username);
    int addUser(User user);
    int refreshDate(Map map);
}
