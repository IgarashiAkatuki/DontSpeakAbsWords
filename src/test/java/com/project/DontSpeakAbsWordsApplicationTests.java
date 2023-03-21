package com.project;

import com.mysql.cj.util.StringUtils;
import com.project.common.utils.JwtUtils;
import com.project.common.utils.Neo4jUtils;
import com.project.constant.Constant;
import com.project.entity.jpa.Persistence;
import com.project.entity.neo4j.SourceNode;
import com.project.repository.jpa.Translation4NeoRepo;
import com.project.repository.neo4j.SourceRepo;
import com.project.service.GraphService;
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
import java.util.List;


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

    @Autowired
    private GraphService graphService;

    @Autowired
    private Translation4NeoRepo repository;

    @Autowired
    private SourceRepo sourceRepo;

    @Autowired
    private Neo4jUtils neo4jUtils;
    @Test
    void updateGraphTest() throws Exception{
            String source ="awa";
            if (StringUtils.isNullOrEmpty(source)){
                return;
            }
            List<SourceNode> sourceNodes = sourceRepo.queryAllByVal(source);
            if (sourceNodes != null && sourceNodes.size() > 0){
                return;
            }
            SourceNode sourceNode = new SourceNode(0,source);
            sourceRepo.save(sourceNode);
            System.out.println("添加了[sourceNode]-> "+ sourceNode.getVal());
    }



}
