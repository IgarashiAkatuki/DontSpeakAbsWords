package com.project.mapper;

import com.project.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface AdminMapper {
    Admin selectAdmin(String username);
    int setEnabled(Map map);
    int setAuthority(Map map);
}
