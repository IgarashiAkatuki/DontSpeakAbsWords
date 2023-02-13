package com.project.controller;

import com.mysql.cj.util.StringUtils;
import com.project.common.response.ErrorInfo;
import com.project.common.response.ResponseStatusCode;
import com.project.common.response.Result;
import com.project.common.utils.FuzzyQueryUtils;
import com.project.common.utils.RegexUtils;
import com.project.entity.Erratum;
import com.project.entity.Source;
import com.project.entity.Translation;
import com.project.entity.Word;
import com.project.pojo.SourceAO;
import com.project.pojo.TempTranslations;
import com.project.pojo.TranslationAO;
import com.project.service.ErratumService;
import com.project.service.SourceService;
import com.project.service.TranslationService;
import com.project.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BackStageController {

    @Autowired
    @Qualifier("erratumServiceImpl")
    private ErratumService erratumService;

    @Autowired
    @Qualifier("sourceServiceImpl")
    private SourceService sourceService;

    @Autowired
    @Qualifier("translationServiceImpl")
    private TranslationService translationService;

    @Autowired
    @Qualifier("wordServiceImpl")
    private WordService wordService;

    @Autowired
    @Qualifier("fuzzyQueryUtils")
    private FuzzyQueryUtils fuzzyQueryUtils;


    @GetMapping("/erratumInfo")
    @ResponseBody
    public Result getErratumInfo(){
        List<Erratum> errata = erratumService.queryAllErratum();
        return Result.suc(errata);
    }

    @GetMapping("/sourceInfo")
    @ResponseBody
    public Result getSourceInfo(){
        List<Source> sources = sourceService.queryAllSource();
        return Result.suc(sources);
    }

    @PostMapping("/submitSource")
    @ResponseBody
    public Result submitSource(@Validated SourceAO tempSource, BindingResult result){
        if (result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(),ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }else {
            if (!tempSource.getSource().isEmpty()){
                int nums = sourceService.isInPS(tempSource.getTranslation());
                System.out.println(nums);
                if (nums <= 0){
                    return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(),ResponseStatusCode.FAILED.getResultMsg()));
                }
                int info = sourceService.submitSourceToTransl(tempSource.getTranslation(), tempSource.getSource(), tempSource.getUrlOrDefault("null"));
                System.out.println(info);
                if (info != 1){
                    return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(),ResponseStatusCode.FAILED.getResultMsg()));
                }
                return Result.suc();
            }else {
                return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(),ResponseStatusCode.FAILED.getResultMsg()));
            }
        }
    }

    @PostMapping("/deleteTranslation")
    @ResponseBody
    public Result deleteTranslation(@Validated TempTranslations translations, BindingResult result){
            int info = translationService.deleteTranslInPS(translations.getWord(), translations.getTranslation());
            if (info != 1){
                return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(),ResponseStatusCode.FAILED.getResultMsg()));
            }else {
                erratumService.deleteErratumByTransl(translations.getTranslation());
                return Result.suc();
            }

    }

    @PostMapping("/updateTranslation")
    @ResponseBody
    public Result updateTranslation(@Validated TempTranslations translations){
            if (!translations.getNewTranslation().isEmpty()){
                int info = translationService.updateTranslInPS(translations.getWord(), translations.getNewTranslation(), translations.getTranslation());
                if (info != 1){
                    return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(),ResponseStatusCode.FAILED.getResultMsg()));
                }else {
                    erratumService.deleteErratumById(translations.getId());
                    return Result.suc();
                }
            }else {
                return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(),ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
            }
    }


    @ConditionalOnBean(
            name = "fuzzyQueryUtils"
    )
    @PostMapping("/submitTranslToPS")
    @ResponseBody
    public Result submitTranslToPS(@Validated TranslationAO translationAO){


        String translation = RegexUtils.removeExtraSpace(translationAO.getTranslation());
        String word = RegexUtils.replaceSpaceToUnderscore(translationAO.getWord());

        if (StringUtils.isNullOrEmpty(translation) || StringUtils.isNullOrEmpty(word)){
            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(),ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        Word temp = wordService.queryWordByName(word);
        if (ObjectUtils.isEmpty(temp)){
            Word tempWord = new Word();
            tempWord.setLikes(1);
            tempWord.setDate(new Date());
            tempWord.setWord(word);
            int flag = wordService.addWord(tempWord);
        }
        temp = wordService.queryWordByName(word);
        String fuzzyWord = fuzzyQueryUtils.setFuzzyWord(word);

        Translation transl = new Translation();
        transl.setDate(new Date());
        transl.setLikes(1);
        transl.setWord(word);
        transl.setWordId(temp.getWordId());
        transl.setTranslation(translation);
        transl.setFuzzyWord(fuzzyWord);
        transl.setUrl(translationAO.getUrl());
        if (!StringUtils.isNullOrEmpty(translationAO.getSource())){
            transl.setSource(translationAO.getSource());
        }
        int flag = translationService.addTranslToPsAdmin(transl);

        if (flag == 1){
            return Result.suc();
        }else {
            return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(),ResponseStatusCode.FAILED.getResultMsg()));
        }
    }
}
