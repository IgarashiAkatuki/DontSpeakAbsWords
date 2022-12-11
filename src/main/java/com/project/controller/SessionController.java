package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping("/api")
public class SessionController {

    // 通过session判断是否点赞过，用于前端视图渲染
    @ApiOperation(value = "翻译是否被点赞")
    @PostMapping(value = "/isLiked",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String getTranslationSession(@ApiParam("翻译") @RequestParam(value = "translations") String[] translations, HttpServletRequest request) throws Exception{

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
}
