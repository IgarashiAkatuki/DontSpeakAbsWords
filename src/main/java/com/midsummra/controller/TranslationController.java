package com.midsummra.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midsummra.pojo.Translation;
import com.midsummra.service.TranslationService;
import com.midsummra.service.WordService;
import com.midsummra.utils.RegexUtils;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class TranslationController {
    @Autowired
    @Qualifier("translationServiceImpl")
    private TranslationService translationService;

    @Autowired
    @Qualifier("wordServiceImpl")
    private WordService wordService;


    //从暂存区获得释义
    @RequestMapping(value = "/getTranslationsFromTemp",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String getTranslationsFromTemp(String word) throws Exception{

        word = RegexUtils.replaceSpaceToUnderscore(word);
        List<Translation> translations = translationService.queryTranslationFromTemp(word);

        String json = null;
        if (!ObjectUtils.isEmpty(translations)){
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(translations);
        }else {
            HashMap<String, String> map = new HashMap<>();
            map.put("info","0");
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(map);
        }

        return json;
    }



    //从持久区获得释义
    @RequestMapping(value = "/getTranslationsFromPersistence",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String getTranslationsFromPersistence(String word) throws Exception{
        word = RegexUtils.replaceSpaceToUnderscore(word);
        List<Translation> translations = translationService.queryTranslationFromTemp(word);

        String json = null;
        if (!ObjectUtils.isEmpty(translations)){
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(translations);
        }else {
            HashMap<String, String> map = new HashMap<>();
            map.put("info","0");
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(map);
        }

        return json;
    }



    //向暂存区中提交释义
    @RequestMapping(value = "/submitTranslationsToTemp",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String submitTranslationToTemp(String word,String translation) throws Exception{
        int flag = 0;
        String temp = translation;
        HashMap<String, String> map = new HashMap<>();
        if (StringUtils.isNullOrEmpty(temp.replace(" ",""))){
            map.put("info","0");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(map);
            return json;
        }


        //去除多余的空格
        translation = RegexUtils.removeExtraSpace(translation);
        word = RegexUtils.replaceSpaceToUnderscore(word);

        int wordId = -1;

        if (ObjectUtils.isEmpty(translationService.queryTranslationByTranslationInTemp(translation))){

            wordId = wordService.getWordId(word);

            Translation tempTranslation = new Translation();
            tempTranslation.setTranslation(translation);
            tempTranslation.setWord(word);
            tempTranslation.setLikes(1);
            tempTranslation.setDate(new Date());
            tempTranslation.setWordId(wordId);

            flag = translationService.addTranslationToTemp(tempTranslation);

        }else {
            flag = translationService.addLikesToTemp(translation);
            //查询此释义现在的like数
            if (translationService.queryTranslationLikes(translation) >= 5){
                //如果持久表中已有释义
                if (!ObjectUtils.isEmpty(translationService.queryTranslationByTranslationInPersistence(translation))){

                    flag = translationService.addLikesToPersistence(translation);

                }else {
                    Translation tempTranslation = new Translation();
                    tempTranslation.setWord(word);
                    tempTranslation.setTranslation(translation);
                    tempTranslation.setDate(new Date());
                    tempTranslation.setLikes(0);
                    tempTranslation.setWordId(wordId);
                    flag = translationService.addTranslationToPersistence(tempTranslation);
                }

            }

        }

        if (flag == 1){
            map.put("info","1");
        }else {
            map.put("info","0");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(map);

        return json;
    }



    //向暂存区中提交点赞
    @RequestMapping(value = "/addLikesToTemp",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String addLikesToTemp(String translation) throws Exception{
        translation = RegexUtils.removeExtraSpace(translation);

        HashMap<String, String> map = new HashMap<>();
        int flag = 0;
        flag = translationService.addLikesToTemp(translation);

        if (flag == 1){
            map.put("info","1");
        }else {
            map.put("info","0");
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);

        return json;
    }



    //向持久区中释义提交点赞
    @RequestMapping(value = "/addLikesToPersistence",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String addLikesToPersistence(String translation,HttpServletRequest request) throws Exception{
        translation = RegexUtils.removeExtraSpace(translation);

        HttpSession session = request.getSession();
        HashMap<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        int flag = 0;

        Object attribute = session.getAttribute(translation);

        if (ObjectUtils.isEmpty(attribute) || "0".equals((String) attribute)){

            flag = translationService.addLikesToPersistence(translation);
            session.setAttribute(translation,"1");

        }else if ("1".equals((String) attribute)){

            flag = translationService.removeLikesInPersistence(translation);
            session.setAttribute(translation,"0");

        }else {
            session.setAttribute(translation,"0");
        }

        if (flag == 1){
            map.put("info","1");
        }else {
            map.put("info","0");
        }

        String json = mapper.writeValueAsString(map);

        return json;

//        if (ObjectUtils.isEmpty(session.getAttribute(translation)) || "0".equals(session.getAttribute(translation))){
//            flag = translationService.addLikesToPersistence(translation);
//        }
//
//        if (flag == 1){
//            session.setAttribute(translation,"1");
//            map.put("info","1");
//        }else {
//            map.put("info","0");
//        }
//
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(map);
//
//        return json;
    }

    @RequestMapping("/temp")
    @ResponseBody
    public String testTemp(String translation) throws JsonProcessingException {
        translation = RegexUtils.removeExtraSpace(translation);
        HashMap<String, String> map = new HashMap<>();
//        int flag = 0;
        if (!ObjectUtils.isEmpty(translationService.queryTranslationByTranslationInPersistence(translation))){
//            flag = 1;
            map.put("info","1");
        }else {
            map.put("info","0");
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);

        return json;

    }


}
