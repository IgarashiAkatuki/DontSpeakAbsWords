package com.midsummra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api")
public class SessionController {

    @RequestMapping(value = "/isLiked",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String getTranslationSession(String[] translations, HttpServletRequest request) throws Exception{

        HashMap<String, String> map = new HashMap<>();
        HttpSession session = request.getSession();

        for (String translation : translations) {
            if (session.getAttribute(translation).equals("0") || ObjectUtils.isEmpty(session.getAttribute(translation))){
                map.put(translation,"0");
            }else {
                map.put(translation,"1");
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);

        return json;
    }
}
