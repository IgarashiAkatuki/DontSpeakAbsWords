package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DontSpeakAbsWordsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DontSpeakAbsWordsApplication.class, args);
    }

}
