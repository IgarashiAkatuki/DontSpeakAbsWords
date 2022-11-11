package com.midsummra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.midsummra.constant.Constant;
import com.midsummra.pojo.Word;
import com.midsummra.service.WordService;
import com.midsummra.utils.RegexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
public class WordController {
    @Autowired
    @Qualifier("wordServiceImpl")
    private WordService wordService;

    @RequestMapping(value = "/returnWords",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String returnWords() throws Exception {


        List<Word> words = wordService.queryAllWord();

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(words);
        System.out.println(s);
        return s;
    }

    @RequestMapping(value = "/addWords",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String addWords(String word) throws Exception{

        word = RegexUtils.replaceSpaceToUnderscore(word);

        int returnValue = 0;
        HashMap<String, String> map = new HashMap<>();
        Word temp = wordService.queryWordByName(word);

        if (ObjectUtils.isEmpty(temp)){
            Word tempWord = new Word();
            tempWord.setWord(word);
            tempWord.setLikes(1);
            returnValue = wordService.addWord(tempWord);
        }else {
            returnValue = wordService.addLikes(word);
        }

        if (returnValue == 0){
            map.put("info","0");
        }else {
            map.put("info","1");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(map);

        return json;
    }

    @RequestMapping(value = "/queryWord",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String queryWord(String word) throws Exception{

        word = RegexUtils.replaceSpaceToUnderscore(word);

        HashMap<String, String> map = new HashMap<>();
        Word tempWord = wordService.queryWordByName(word);

        if (ObjectUtils.isEmpty(tempWord)){
            map.put("info","0");
        }else {
            map.put("info","1");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(map);

        return json;
    }

    @RequestMapping("/getRandomQuestionnaire")
    @ResponseBody
    public String generateRandomQuestionnaire() throws Exception{
        List<Word> randomWords = wordService.getRandomWords(Constant.LikeThresholds, Constant.QuestionnaireLimits);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(randomWords);

        return json;
    }
}
