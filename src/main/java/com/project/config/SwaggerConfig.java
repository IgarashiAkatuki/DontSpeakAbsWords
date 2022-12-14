package com.project.config;

import com.project.constant.Constant;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

import static springfox.documentation.spi.DocumentationType.OAS_30;

@Configuration
//@EnableWebMvc
// 当配置文件中config.swagger为true时开启swagger
@ConditionalOnProperty(
        name = "config.swagger",
        havingValue = "true"
)
public class SwaggerConfig {

    @Autowired
    private Constant constant;


    @Bean
    public Docket docket(){
        return new Docket(OAS_30)
                .enable(constant.isEnableSwagger())
                .groupName("DontSpeakAbsWordsAPI")
                .apiInfo(new ApiInfo("DontSpeakAbsWordsAPI",
                        "DontSpeakAbsWords接口文档(不包含admin接口)",
                        "1.2.0",
                        "urn:tos",
                        new Contact("Midsummra", "https://github.com/IgarashiAkatuki", "midsummra@gmail.com"),
                        "GPL 3.0",
                        "https://www.gnu.org/licenses/gpl-3.0.html",
                        new ArrayList()))
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .paths(PathSelectors.ant("/api/**"))
                .build();
    }
}
