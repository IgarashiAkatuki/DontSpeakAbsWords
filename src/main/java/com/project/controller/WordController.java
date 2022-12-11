package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.constant.Constant;
import com.project.entity.Word;
import com.project.pojo.QuestionnaireAO;
import com.project.pojo.WordAO;
import com.project.service.WordService;
import com.project.utils.RegexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api")
public class WordController {

    @Autowired
    @Qualifier("constant")
    private Constant constant;

    @Autowired
    @Qualifier("wordServiceImpl")
    private WordService wordService;

    // 获取全部的词条数据
    @RequestMapping(value = "/admin/returnWords",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String returnWords() throws Exception {

        List<Word> words = wordService.queryAllWord();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(words);

        return json;
    }

    @RequestMapping(value = "/addWords",produces = "text/html;charset = utf-8")
    @ResponseBody
    //使用JSR303验证

    public String addWords(@Valid WordAO words, BindingResult result) throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> map = new HashMap<>();

        // JSR303验证失败直接返回错误原因[info]
        if (result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();

            map.put("info",defaultMessage);
            return mapper.writeValueAsString(map);
        }

        String word = words.getWord();
        // 如果word去除首尾空格后为空，错误原因[info]
        word = word.trim();
        if (word.isEmpty()){
            map.put("info","输入的值不能为空");
            return mapper.writeValueAsString(map);
        }

        word = RegexUtils.removeExtraSpace(word);

        // 验证此词条是否已经存在
        Word temp = wordService.queryWordByName(word);


        int returnValue = 0;
        if (ObjectUtils.isEmpty(temp)){
            // 不存在
            Word tempWord = new Word();
            tempWord.setWord(word);
            tempWord.setLikes(1);
            tempWord.setDate(new Date());

            returnValue = wordService.addWord(tempWord);
        }else {
            // 存在-> like+1
            returnValue = wordService.addWordLike(word);
        }

        map.put("info",returnValue+"");

        return mapper.writeValueAsString(map);
    }
    @RequestMapping(value = "/queryWord",produces = "text/html;charset = utf-8")
    @ResponseBody
    @Valid
    public String queryWord(@Valid WordAO words, BindingResult result) throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> map = new HashMap<>();

        // JSR303验证失败直接返回错误原因[info]
        if (result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();

            map.put("info",defaultMessage);
            return mapper.writeValueAsString(map);
        }

        String word = words.getWord();
        // 如果word去除首尾空格后为空，错误原因[info]
        word = word.trim();
        if (word.isEmpty()){
            map.put("info","输入的值不能为空");
            return mapper.writeValueAsString(map);
        }


        Word temp = wordService.queryWordByName(word);

        if (ObjectUtils.isEmpty(temp)){
            map.put("info","0");
        }else {
            map.put("info","1");
        }

        return mapper.writeValueAsString(map);
    }

    @RequestMapping("/getRandomQuestionnaire")
    @ResponseBody

    public String generateRandomQuestionnaire(@Valid QuestionnaireAO questionnaireAO, HttpServletRequest request, BindingResult result) throws Exception{

        HashMap<String, String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        if (result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            String defaultMessage = fieldErrors.get(0).getDefaultMessage();

            map.put("info",defaultMessage);
            return mapper.writeValueAsString(map);

        }

        int questionnaireLimits = constant.getQuestionnaireLimits();
        if (questionnaireAO.getLimit() != 0){
            questionnaireLimits = questionnaireAO.getLimit();
        }

        // 获取用户session
        HttpSession session = request.getSession();
        Object temp = session.getAttribute("timestamp");
        List<Word> randomWords = null;

        if (ObjectUtils.isEmpty(temp)){

            randomWords = wordService.getRandomWords(constant.getLikeThresholds(),questionnaireLimits);
            session.setAttribute("timestamp",new Date());

        }else {
            Date timestamp = (Date) temp;
            Date currentDate = new Date();
            long times = currentDate.getTime()-timestamp.getTime();

            System.out.println(timestamp);
            System.out.println(times);
            if (times >= constant.getQuestionnaireCoolDown()){

                randomWords = wordService.getRandomWords(constant.getLikeThresholds(), questionnaireLimits);
                session.setAttribute("timestamp",currentDate);

            }else {

                long interval = (constant.getQuestionnaireCoolDown()-times)/1000;
                map.put("info","还有"+interval/60+"分"+(interval%60)+"秒，可以再次获取问卷");

                String json = mapper.writeValueAsString(map);

                return json;
            }
        }

        String json = mapper.writeValueAsString(randomWords);

        return json;
    }
}
