package com.midsummra.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midsummra.constant.Constant;
import com.midsummra.pojo.Translation;
import com.midsummra.pojo.Word;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        if (StringUtils.isNullOrEmpty(word)){

            HashMap<String, String> map = new HashMap<>();
            map.put("info","0");
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(map);

        }

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

        if (StringUtils.isNullOrEmpty(word)){

            HashMap<String, String> map = new HashMap<>();
            map.put("info","0");
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(map);

        }
        List<Translation> translations = translationService.queryTranslationFromPersistence(word);

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


        if (StringUtils.isNullOrEmpty(temp)||StringUtils.isNullOrEmpty(temp.replace(" ",""))){
            map.put("info","0");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(map);
            return json;
        }


        //去除多余的空格
        translation = RegexUtils.removeExtraSpace(translation);
        word = RegexUtils.replaceSpaceToUnderscore(word);

        //如果持久区中有该释义，则直接给次释义的like+1;
        if (!ObjectUtils.isEmpty(translationService.queryTranslationByTranslationInPersistenceFixed(word,translation))){

            ObjectMapper mapper = new ObjectMapper();
            flag = translationService.addLikesToPersistenceFixed(word,translation);

            if (flag == 1){
                map.put("info","1");
            }else {
                map.put("info","0");
            }

            String json = mapper.writeValueAsString(map);
            return json;
        }

        int wordId = -1;

        if (ObjectUtils.isEmpty(translationService.queryTranslationByTranslationInTempFixed(word,translation))){

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

            flag = translationService.addTranslationToTemp(tempTranslation);

        }else {
            flag = translationService.addLikesToTempFixed(word,translation);
            //查询此释义现在的like数
            if (translationService.queryTranslationLikesFixed(word,translation) >= Constant.TransformThresholds){
                //如果持久表中已有释义
                if (!ObjectUtils.isEmpty(translationService.queryTranslationByTranslationInPersistenceFixed(word,translation))){

                    flag = translationService.addLikesToPersistenceFixed(word,translation);

                }else {

                    wordId = wordService.getWordId(word);

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



    // 向暂存区中提交点赞
    // 此方法于2022.11.22修改 新增需要word参数
    @RequestMapping(value = "/addLikesToTemp",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String addLikesToTemp(String word, String translation) throws Exception{
        translation = RegexUtils.removeExtraSpace(translation);
        word = RegexUtils.replaceSpaceToUnderscore(word);

        HashMap<String, String> map = new HashMap<>();
        int flag = 0;
        flag = translationService.addLikesToTempFixed(word,translation);

        if (flag == 1){
            map.put("info","1");
        }else {
            map.put("info","0");
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);

        return json;
    }



    //向持久区中释义提交点赞(使用session)
    // 此方法于2022.11.22修改 新增需要word参数
    @RequestMapping(value = "/addLikesToPersistence",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String addLikesToPersistence(String word, String translation, HttpServletRequest request) throws Exception{

        translation = RegexUtils.removeExtraSpace(translation);
        word = RegexUtils.replaceSpaceToUnderscore(word);

        HttpSession session = request.getSession();
        HashMap<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        int flag = 0;

        Object attribute = session.getAttribute(translation);

        if (ObjectUtils.isEmpty(attribute) || "0".equals((String) attribute)){

            flag = translationService.addLikesToPersistenceFixed(word,translation);
            session.setAttribute(translation,"1");

        }else if ("1".equals((String) attribute)){

            flag = translationService.removeLikesInPersistenceFixed(word,translation);
            session.setAttribute(translation,"0");

        }else {
            session.setAttribute(translation,"-1");
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

//    @RequestMapping("/temp")
//    @ResponseBody
//    public String testTemp(String translation) throws JsonProcessingException {
//        translation = RegexUtils.removeExtraSpace(translation);
//        HashMap<String, String> map = new HashMap<>();
////        int flag = 0;
//        if (!ObjectUtils.isEmpty(translationService.queryTranslationByTranslationInPersistence(translation))){
////            flag = 1;
//            map.put("info","1");
//        }else {
//            map.put("info","0");
//        }
//
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(map);
//
//        return json;
//
//    }


    //向持久区中释义提交点赞(使用cookies)
    @RequestMapping(value = "/addLikesToPersistenceByCookies",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String addLikesToPersistenceByCookies(String word, String translation, HttpServletRequest request, HttpServletResponse response) throws Exception {
        translation = RegexUtils.removeExtraSpace(translation);
        word = RegexUtils.replaceSpaceToUnderscore(word);

        HashMap<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        int flag = 0;

        flag = translationService.addLikesToPersistenceFixed(word,translation);

        if (flag == 1){
            map.put("info","1");

            boolean containsCookies = false;

            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("userInfo")){

                    containsCookies = true;

                    cookie.setValue(cookie.getValue()+translation+":"+1+"|");
                    cookie.setMaxAge(365*10000);
                    response.addCookie(cookie);
                    System.out.println(cookie);

                    break;
                }
            }

            if (!containsCookies){
                Cookie cookie = new Cookie("userInfo","|"+translation+":"+1+"|");
                cookie.setMaxAge(365*10000);
                response.addCookie(cookie);
            }

        }else {
            map.put("info","0");
        }

        String json = mapper.writeValueAsString(map);

        return json;
    }

}
