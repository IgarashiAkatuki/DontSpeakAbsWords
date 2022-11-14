package com.midsummra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String getTranslationSession(@RequestParam(value = "translations") String[] translations, HttpServletRequest request) throws Exception{

        HashMap<String, String> map = new HashMap<>();
        HttpSession session = request.getSession();

        for (int i = 0; i < translations.length; i++) {
            if (ObjectUtils.isEmpty(session.getAttribute(translations[i]))|| ((String)session.getAttribute(translations[i])).equals("0")){
                map.put(translations[i],"0");
            }else {
                map.put(translations[i],"1");
            }

        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);

        return json;
    }
}
