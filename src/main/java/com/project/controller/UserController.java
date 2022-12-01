package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.constant.WebConstant;
import com.project.pojo.User;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;


@Controller

public class UserController {
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;


    @Autowired
    @Qualifier("webConstant")
    private WebConstant webConstant;


    @RequestMapping(value = "/api/verifyUser",produces = "text/html;charset = utf-8")
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

//    @RequestMapping(value = "/admin/login")
//    public String login(){
//        return "admin/LoginTest";
//    }
//
//    @RequestMapping(value = "/admin/backstage")
//    public String backstage(){
//        return "admin/backstage";
//    }
//
//    @RequestMapping(value = "index")
//    public String index(){
//        return "index";
//    }
}
