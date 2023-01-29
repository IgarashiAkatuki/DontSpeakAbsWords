package com.project.controller;

import com.mysql.cj.util.StringUtils;
import com.project.common.response.ResponseStatusCode;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/info")
    public String getErratumInfo(Model model){

        List<Erratum> errata = erratumService.queryAllErratum();
        List<Source> sources = sourceService.queryAllSource();
        model.addAttribute("erratum",errata);
        model.addAttribute("sources",sources);

        return "admin/backstage";
    }

    @PostMapping("/deleteTranslation")
    public String deleteTranslation(@Validated TempTranslations translations, BindingResult result, Model model){
        if (result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            model.addAttribute("translationError",fieldErrors.get(0).getDefaultMessage());

            return "forward:/admin/info";
        }else {
            int info = translationService.deleteTranslInPS(translations.getWord(), translations.getTranslation());
            if (info != 1){
                model.addAttribute("translationError","删除失败");
            }else {
                erratumService.deleteErratumByTransl(translations.getTranslation());
                model.addAttribute("translationError","提交成功");
            }

            return "forward:/admin/info";
        }
    }

    @PostMapping("/updateTranslation")
    public String updateTranslation(@Validated TempTranslations translations,BindingResult result,Model model){
        if (result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            model.addAttribute("translationError",fieldErrors.get(0).getDefaultMessage());

            return "forward:/admin/info";
        }else {
            if (!translations.getNewTranslation().isEmpty()){
                int info = translationService.updateTranslInPS(translations.getWord(), translations.getTranslation(), translations.getNewTranslation());
                if (info != 1){
                    model.addAttribute("translationError","更改失败");
                }else {
                    erratumService.deleteErratumById(translations.getId());
                    model.addAttribute("translationError","提交成功");
                }
                return "forward:/admin/info";
            }else {
                model.addAttribute("translationError","新翻译为空");
                return "forward:/admin/info";
            }
        }
    }

    @PostMapping("/submitSource")
    public String submitSource(@Validated SourceAO tempSource, BindingResult result, Model model){
        if (result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            model.addAttribute("sourceError",fieldErrors.get(0).getDefaultMessage());

            return "forward:/admin/info";
        }else {
            if (!tempSource.getSource().isEmpty()){
                int nums = sourceService.isInPS(tempSource.getTranslation());
                if (nums <= 0){
                    model.addAttribute("sourceError","此翻译不存在于持久区");
                    return "forward:/admin/info";
                }
                int info = sourceService.submitSourceToTransl(tempSource.getTranslation(), tempSource.getSource(), tempSource.getUrl());
                if (info != 1){
                    model.addAttribute("sourceError","提交失败");
                }else {
                    model.addAttribute("sourceError","提交成功");
                }
                return "forward:/admin/info";
            }else {
                model.addAttribute("sourceError","来源为空");
                return "forward:/admin/info";
            }
        }
    }

    @ConditionalOnBean(
            name = "fuzzyQueryUtils"
    )
    @PostMapping("/submitTranslToPS")
    public String submitTranslToPS(@Validated TranslationAO translationAO, BindingResult result, Model model){

        if (result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            String s = fieldErrors.get(0).getDefaultMessage();
            model.addAttribute("info",s);

            return "forward:/admin/info";
        }

        String translation = RegexUtils.removeExtraSpace(translationAO.getTranslation());
        String word = RegexUtils.replaceSpaceToUnderscore(translationAO.getWord());

        if (StringUtils.isNullOrEmpty(translation) || StringUtils.isNullOrEmpty(word)){
            model.addAttribute("info", ResponseStatusCode.INVALID_PARAMETER.getResultMsg());
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
        if (!StringUtils.isNullOrEmpty(translationAO.getSource())){
            transl.setSource(translationAO.getSource());
        }
        int flag = translationService.addTranslToPS(transl);

        if (flag == 1){
            model.addAttribute("info",ResponseStatusCode.SUCCESS.getResultMsg());
        }else {
            model.addAttribute("info",ResponseStatusCode.FAILED.getResultMsg());
        }
        return "forward:/admin/info";
    }
}
