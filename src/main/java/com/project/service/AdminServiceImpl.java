package com.project.service;

import com.project.entity.Admin;
import com.project.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    @Qualifier("adminMapper")
    AdminMapper adminMapper;

    @Override
    public Admin selectAdmin(String username) {
        return adminMapper.selectAdmin(username);
    }

    @Override
    public int setEnabled(String username, String enabled) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("username",username);
        map.put("enabled",enabled);

        return adminMapper.setEnabled(map);
    }

    @Override
    public int setAuthority(String username, String authority) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("username",username);
        map.put("authority",authority);

        return adminMapper.setEnabled(map);
    }
}
