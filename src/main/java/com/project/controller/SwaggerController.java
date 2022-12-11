package com.project.controller;

import com.project.pojo.QuestionnaireAO;
import com.project.pojo.TranslationAO;
import com.project.pojo.WordAO;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/swagger")
public class SwaggerController {

    @PostMapping("/questionnaireAO")
    @ApiOperation("QuestionnaireAO")
    public QuestionnaireAO questionnaireAO(){
        return new QuestionnaireAO();
    }

    @PostMapping("/translationAO")
    @ApiOperation("TranslationAO")
    public TranslationAO translationAO(){
        return new TranslationAO();
    }

    @PostMapping("/wordAO")
    @ApiOperation("WordAO")
    public WordAO wordAO(){
        return new WordAO();
    }


}
