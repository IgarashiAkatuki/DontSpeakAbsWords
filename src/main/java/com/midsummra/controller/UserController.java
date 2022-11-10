package com.midsummra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.midsummra.pojo.User;
import com.midsummra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;


    @RequestMapping(value = "/verifyUser",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String VerifyUser(String username, String password , HttpSession session) throws Exception{

        HashMap<String, String> map = new HashMap<>();

        User user = userService.getUser(username);
        if (ObjectUtils.isEmpty(user)){
            map.put("info","0");

        }else {
            if (password.equals(user.getPassword())){
                if (!"admin".equals(user.getPermission())){
                    map.put("info","2");
                }else {
                    Date date = new Date();
                    user.setDate(date);
                    userService.refreshDate(username,date);

                    session.setAttribute("userInfo",user);
                    map.put("info","1");
                }
            }else {
                map.put("info","0");
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);
        return json;
    }

    @RequestMapping("/login")
    public String login(){
        return "admin/LoginTest";
    }

    @RequestMapping("/backstage")
    public String toBackstage(){
        return "admin/backstage";
    }

}

