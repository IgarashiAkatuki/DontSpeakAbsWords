package com.midsummra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/api")
public class SessionController {

    @RequestMapping(value = "/isLiked",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String getTranslationSession(@RequestParam(value = "translations") String[] translations, HttpServletRequest request) throws Exception{

        ArrayList<Integer> list = new ArrayList<>();
        HttpSession session = request.getSession();

        for (int i = 0; i < translations.length; i++) {
            if (ObjectUtils.isEmpty(session.getAttribute(translations[i]))|| ((String)session.getAttribute(translations[i])).equals("0")){
                list.add(0);
            }else {
                list.add(1);
            }

        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(list);

        return json;
    }

    @RequestMapping(value = "/getUsersNum",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String getUsersNum(HttpSession httpSession) throws Exception{
        
        ServletContext servletContext = httpSession.getServletContext();
        int nums = (int)servletContext.getAttribute("sessionSetSize");

        HashMap<String, String> map = new HashMap<>();
        map.put("nums",nums+"");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);

        return json;
    }
}
