package com.project;

import com.project.common.utils.JwtUtils;
import com.project.constant.Constant;
import com.project.service.TranslStatisticsService;
import com.project.service.TranslationService;
import com.project.service.WordService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;


@SpringBootTest
class DontSpeakAbsWordsApplicationTests {

    @Autowired
    private JwtUtils jwtUtils;


    @Test
    void contextLoads() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","midsummra");
        String s = jwtUtils.generateToken(map);
        Claims claims = jwtUtils.getClaims(s);
        System.out.println(claims.getExpiration());

    }


}
