package com.project.controller;

import com.project.common.response.Result;
import com.project.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.expiration}")
    private long expiration;

    @RequestMapping("/login")
    @ResponseBody
    public Result login(String username, String password, HttpServletResponse response){
        if (username == null || password == null){
            return Result.error();
        }
        String token = loginService.login(username, password);

        if (token != null){
            Cookie cookie = new Cookie(tokenHeader, token);
            cookie.setMaxAge((int) expiration);
            cookie.setPath("/");
            response.addCookie(cookie);

            return Result.suc();
        }
        return Result.error();
    }
}
