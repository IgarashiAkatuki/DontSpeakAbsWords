package com.project.mapper;

import com.project.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface UserMapper {

    // 获取用户
    User queryUser(String username);

    // 添加用户
    int addUser(User user);

    // 设置用户可用状态
    // map中的值为: username,flag
    int setUserEnable(Map map);
}
