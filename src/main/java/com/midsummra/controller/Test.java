package com.midsummra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Test {
    @RequestMapping("/toBaidu")
    public String toBaidu(){
        return  "redirect:https://www.baidu.com/";
    }
}
