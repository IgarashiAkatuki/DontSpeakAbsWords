package com.project.service;

import com.project.entity.Admin;

import java.util.Map;

public interface AdminService {
    Admin selectAdmin(String username);
    int setEnabled(String username,String enabled);
    int setAuthority(String username,String authority);
}
