package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;
import com.project.constant.WebConstant;
import com.project.pojo.Word;
import com.project.service.WordService;
import com.project.uitls.RegexUtils;
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
@RequestMapping("/api")

public class WordController {
    @Autowired
    @Qualifier("wordServiceImpl")
    private WordService wordService;

    @Autowired
    @Qualifier("webConstant")
    private WebConstant webConstant;

    @RequestMapping(value = "/admin/returnWords",produces = "text/html;charset = utf-8")
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

        if (StringUtils.isNullOrEmpty(word)){
            HashMap<String, String> map = new HashMap<>();
            map.put("info","0");

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(map);
            return json;
        }

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
    public String generateRandomQuestionnaire(HttpServletRequest request) throws Exception{

        HttpSession session = request.getSession();
        Object temp = session.getAttribute("timestamp");
        List<Word> randomWords = null;

        if (ObjectUtils.isEmpty(temp)){

            randomWords = wordService.getRandomWords(webConstant.getLikeThresholds(), webConstant.getQuestionnaireLimits());
            session.setAttribute("timestamp",new Date());

        }else {
            Date timestamp = (Date) temp;
            Date currentDate = new Date();
            long times = currentDate.getTime()-timestamp.getTime();

            System.out.println(timestamp);
            System.out.println(times);
            if (times >= webConstant.getQuestionnaireCoolDown()){

                randomWords = wordService.getRandomWords(webConstant.getLikeThresholds(), webConstant.getQuestionnaireLimits());
                session.setAttribute("timestamp",currentDate);

            }else {

                HashMap<String, String> map = new HashMap<>();
                long interval = (webConstant.getQuestionnaireCoolDown()-times)/1000;
                map.put("info","还有"+interval/60+"分"+(interval%60)+"秒，可以再次获取问卷");

                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(map);

                return json;
            }
        }


        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(randomWords);

        return json;
    }
}
