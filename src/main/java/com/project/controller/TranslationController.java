package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;
import com.project.constant.Constant;
import com.project.entity.Translation;
import com.project.entity.Word;
import com.project.pojo.TranslationAO;
import com.project.pojo.WordAO;
import com.project.service.TranslationService;
import com.project.service.WordService;
import com.project.utils.RegexUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api")
public class TranslationController {

    @Autowired
    @Qualifier("translationServiceImpl")
    private TranslationService translationService;

    @Autowired
    @Qualifier("wordServiceImpl")
    private WordService wordService;

    @Autowired
    @Qualifier("constant")
    private Constant webConstant;

    // 从暂存区获得释义
    @ApiOperation("从暂存区获得释义")
    @PostMapping(value = "/getTranslationsFromTemp",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String getTranslationsFromTemp( @Valid WordAO wordAO, BindingResult result) throws Exception{

        HashMap<String, String> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        // JSR303验证失败直接返回错误原因[info]
        if (result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();

            map.put("info",defaultMessage);
            return objectMapper.writeValueAsString(map);
        }

        // 获取word值
        String word = wordAO.getWord();

        // 验证word是否为空
        word = word.trim();
        if (word.isEmpty()){
            map.put("info","输入的值不能为空");
            return objectMapper.writeValueAsString(map);
        }

        word = RegexUtils.replaceSpaceToUnderscore(word);

        if (StringUtils.isNullOrEmpty(word)){

            map.put("info","0");
            return objectMapper.writeValueAsString(map);

        }

        List<Translation> translations = translationService.queryTranslInTempByWord(word);
        String json = null;

        if (!ObjectUtils.isEmpty(translations)){

            json = objectMapper.writeValueAsString(translations);
        }else {

            map.put("info","0");
            json = objectMapper.writeValueAsString(map);
        }

        return json;
    }

    // 从持久区获得释义
    @ApiOperation("从持久区获得释义")
    @PostMapping(value = "/getTranslationsFromPersistence",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String getTranslationsFromPersistence(@Valid WordAO wordAO,BindingResult result) throws Exception{

        HashMap<String, String> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        // JSR303验证失败直接返回错误原因[info]
        if (result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();

            map.put("info",defaultMessage);
            return objectMapper.writeValueAsString(map);
        }

        // 获取word值
        String word = wordAO.getWord();

        // 验证word是否为空
        word = word.trim();
        if (word.isEmpty()){
            map.put("info","输入的值不能为空");
            return objectMapper.writeValueAsString(map);
        }

        word = RegexUtils.replaceSpaceToUnderscore(word);

        if (StringUtils.isNullOrEmpty(word)){

            map.put("info","0");
            return objectMapper.writeValueAsString(map);

        }

        // 获取释义
        List<Translation> translations = translationService.queryTranslInPSByWord(word);
        String json = null;

        // 如果存在释义
        if (!ObjectUtils.isEmpty(translations)){

            json = objectMapper.writeValueAsString(translations);

        }else {

            map.put("info","0");
            json = objectMapper.writeValueAsString(map);
        }

        return json;
    }

    //向暂存区中提交释义
    @ApiOperation("向暂存区中提交释义")
    @PostMapping(value = "/submitTranslationsToTemp",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String submitTranslationToTemp(@Valid TranslationAO translationAO,BindingResult result) throws Exception{

        HashMap<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        // JSR303验证失败直接返回错误原因[info]
        if (result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();

            map.put("info",defaultMessage);
            return mapper.writeValueAsString(map);
        }

        int flag = 0;
//        if (StringUtils.isNullOrEmpty(temp)||StringUtils.isNullOrEmpty(temp.replace(" ",""))){
//            map.put("info","0");
//            ObjectMapper mapper = new ObjectMapper();
//            String json = mapper.writeValueAsString(map);
//            return json;
//        }

        String translation = translationAO.getTranslation();
        String word = translationAO.getWord();

        // 如果word和translation去除首尾空格后为空，错误原因[info]
        word = word.trim();
        translation = translation.trim();
        if (word.isEmpty() || translation.isEmpty()){
            map.put("info","输入的值不能为空");
            return mapper.writeValueAsString(map);
        }

        // 获得translation和word数据并且去除多余的空格
        translation = RegexUtils.removeExtraSpace(translation);
        word = RegexUtils.replaceSpaceToUnderscore(word);

        //如果持久区中有该释义，则直接给次释义的like+1;
        if (!ObjectUtils.isEmpty(translationService.queryTranslInPS(word,translation))){

            flag = translationService.addTranslLikeInPS(word,translation);

            if (flag == 1){
                map.put("info","1");
            }else {
                map.put("info","0");
            }

            String json = mapper.writeValueAsString(map);
            return json;
        }

        int wordId = -1;

        // 如果暂存区中没有此翻译
        if (ObjectUtils.isEmpty(translationService.queryTranslInTemp(word,translation))){

            // 如果不存在这个词条，则添加这个词条并且添加这个词条的翻译到暂存区
            if (ObjectUtils.isEmpty(wordService.queryWordByName(word))){

                Word wordBean = new Word();
                wordBean.setLikes(1);
                wordBean.setWord(word);
                wordBean.setDate(new Date());

                flag = wordService.addWord(wordBean);
            }

            wordId = wordService.getWordId(word);

            Translation tempTranslation = new Translation();
            tempTranslation.setTranslation(translation);
            tempTranslation.setWord(word);
            tempTranslation.setLikes(1);
            tempTranslation.setDate(new Date());
            tempTranslation.setWordId(wordId);

            flag = translationService.addTranslToTemp(tempTranslation);

        }else {
            flag = translationService.addLikeToTemp(word,translation);
            //查询此释义现在的like数
            if (translationService.queryTranslLikeInTemp(word,translation) >= webConstant.getTransformThresholds()){
                //如果持久表中已有释义
                if (!ObjectUtils.isEmpty(translationService.queryTranslInPS(word,translation))){

                    flag = translationService.addTranslLikeInPS(word,translation);

                }else {

                    wordId = wordService.getWordId(word);

                    Translation tempTranslation = new Translation();
                    tempTranslation.setWord(word);
                    tempTranslation.setTranslation(translation);
                    tempTranslation.setDate(new Date());
                    tempTranslation.setLikes(0);
                    tempTranslation.setWordId(wordId);
                    flag = translationService.addTranslToPS(tempTranslation);
                }
            }
        }

        if (flag == 1){
            map.put("info","1");
        }else {
            map.put("info","0");
        }

        String json = mapper.writeValueAsString(map);

        return json;
    }

    //向持久区中释义提交点赞(使用session)
    @ApiOperation("向持久区中释义提交点赞")
    @PostMapping(value = "/addLikesToPersistence",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String addLikesToPersistence(@Valid TranslationAO translationAO,BindingResult result, HttpServletRequest request)throws Exception {

        HashMap<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        HttpSession session = request.getSession();

        // JSR303验证失败直接返回错误原因[info]
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();

            map.put("info", defaultMessage);
            return mapper.writeValueAsString(map);
        }
        String word = translationAO.getWord();
        String translation = translationAO.getTranslation();

        // 如果word和translation去除首尾空格后为空，错误原因[info]
        word = word.trim();
        translation = translation.trim();
        if (word.isEmpty() || translation.isEmpty()) {
            map.put("info", "输入的值不能为空");
            return mapper.writeValueAsString(map);
        }

        translation = RegexUtils.removeExtraSpace(translation);
        word = RegexUtils.replaceSpaceToUnderscore(word);

        int flag = 0;
        Object attribute = session.getAttribute(translation);

        if (ObjectUtils.isEmpty(attribute) || "0".equals((String) attribute)) {

            flag = translationService.addTranslLikeInPS(word, translation);
            session.setAttribute(translation, "1");

        } else if ("1".equals((String) attribute)) {

            flag = translationService.deleteTranslLikeInPS(word, translation);
            session.setAttribute(translation, "0");

        } else {
            session.setAttribute(translation, "-1");
        }

        if (flag == 1) {
            map.put("info", "1");
        } else {
            map.put("info", "0");
        }

        String json = mapper.writeValueAsString(map);

        return json;
    }

}
