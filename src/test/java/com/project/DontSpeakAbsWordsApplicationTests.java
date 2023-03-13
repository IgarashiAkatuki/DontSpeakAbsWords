package com.project;

import com.project.common.utils.JwtUtils;
import com.project.constant.Constant;
import com.project.service.TranslStatisticsService;
import com.project.service.TranslationService;
import com.project.service.WordService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jClient;

import java.util.HashMap;


@SpringBootTest
class DontSpeakAbsWordsApplicationTests {

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${config.neo4jURL}")
    private String neo4jURL;

    @Value("${spring.neo4j.authentication.username}")
    private String username;

    @Value("${spring.neo4j.authentication.password}")
    private String password;



}
