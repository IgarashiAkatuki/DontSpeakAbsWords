package com.project.service;

import com.project.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class AdminDetailService implements UserDetailsService {

    @Autowired
    @Qualifier("adminServiceImpl")
    AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin = adminService.selectAdmin(username);

        if (!ObjectUtils.isEmpty(admin)){
            return admin;
        }else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }
}
