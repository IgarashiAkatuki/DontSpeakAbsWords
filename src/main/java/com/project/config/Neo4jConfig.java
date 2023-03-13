package com.project.config;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.Neo4jClient;

@Configuration
@Slf4j
public class Neo4jConfig {

    @Value("${config.neo4jURL}")
    private String neo4jURL;

    @Value("${spring.neo4j.authentication.username}")
    private String username;

    @Value("${spring.neo4j.authentication.password}")
    private String password;

    @Bean
    public Neo4jClient neo4jClient(){
        Neo4jClient neo4jClient = null;
        try{
            Driver neo4j = GraphDatabase.driver(neo4jURL, AuthTokens.basic(username,password));
            neo4jClient = Neo4jClient.create(neo4j);
        }catch (Exception e){
            e.printStackTrace();
        }

        return neo4jClient;
    }

}
