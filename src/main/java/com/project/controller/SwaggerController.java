package com.project.controller;

import com.project.common.response.ResponseStatusCode;
import com.project.common.response.Result;
import com.project.pojo.QuestionnaireAO;
import com.project.pojo.TranslationAO;
import com.project.pojo.WordAO;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping("/Result")
    @ApiOperation("Result")
    public Result result(){
        return new Result<>();
    }

}
