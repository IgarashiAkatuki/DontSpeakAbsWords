package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.response.ErrorInfo;
import com.project.common.response.ResponseStatusCode;
import com.project.common.response.Result;
import com.project.constant.Constant;
import com.project.entity.mysql.Word;
import com.project.pojo.QuestionnaireAO;
import com.project.pojo.WordAO;
import com.project.service.WordService;
import com.project.common.utils.RegexUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
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
    @PostMapping(value = "/admin/returnWords",produces = "text/html;charset = utf-8")
    @ResponseBody
    public String returnWords() throws Exception {

        List<Word> words = wordService.queryAllWord();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(words);

        return json;
    }

    @ApiOperation("添加词条")
    @PostMapping(value = "/addWords")
    @ResponseBody
    //使用JSR303验证
    public Result addWords(@Valid WordAO words) {

//        // JSR303验证失败直接返回错误原因[info]
//        if (result.hasErrors()){
//            List<FieldError> fieldErrors = result.getFieldErrors();
//            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
//
//            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), defaultMessage));
//        }

        String word = words.getWord();
        // 如果word去除首尾空格后为空，错误原因[info]
        word = word.trim();
        if (word.isEmpty()){

            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));

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

        if (returnValue == 1){

            return Result.suc();
        }else {
            return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(), ResponseStatusCode.FAILED.getResultMsg()));
        }
    }

    @ApiOperation("查询词条")
    @PostMapping(value = "/queryWord")
    @ResponseBody
    @Valid
    public Result queryWord(@Valid WordAO words){

//        // JSR303验证失败直接返回错误原因[info]
//        if (result.hasErrors()){
//            List<FieldError> fieldErrors = result.getFieldErrors();
//            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
//
//            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), defaultMessage));
//        }

        String word = words.getWord();
        // 如果word去除首尾空格后为空，错误原因[info]
        word = word.trim();
        if (word.isEmpty()){

            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }


        Word temp = wordService.queryWordByName(word);

        if (ObjectUtils.isEmpty(temp)){
            return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(), ResponseStatusCode.FAILED.getResultMsg()));
        }else {
            return Result.suc();
        }
    }

    @ApiOperation("获取随机问卷")
    @PostMapping("/getRandomQuestionnaire")
    @ResponseBody
    public Result generateRandomQuestionnaire(@Valid QuestionnaireAO questionnaireAO, HttpServletRequest request){

//
//        if (result.hasErrors()){
//            List<FieldError> fieldErrors = result.getFieldErrors();
//            String defaultMessage = fieldErrors.get(0).getDefaultMessage();
//
//            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), defaultMessage));
//
//        }

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
                return Result.error(new ErrorInfo(100,"还有"+interval/60+"分"+(interval%60)+"秒，可以再次获取问卷"));
            }
        }

        return Result.suc(randomWords);
    }
}
