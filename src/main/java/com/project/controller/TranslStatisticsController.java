package com.project.controller;

import com.project.common.response.ErrorInfo;
import com.project.common.response.ResponseStatusCode;
import com.project.common.response.Result;
import com.project.entity.TranslStatistics;
import com.project.entity.Translation;
import com.project.pojo.TranslStatisticsAO;
import com.project.pojo.TranslationAO;
import com.project.service.TranslStatisticsService;
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
import java.util.HashSet;

@Controller
@RequestMapping("/api")
public class TranslStatisticsController {

    @Autowired
    @Qualifier("translStatisticsServiceImpl")
    private TranslStatisticsService translStatisticsService;

    @ResponseBody
    @PostMapping("/getStatistics")
    @ApiOperation("获得释义数据")
    public Result getStatistics(@Valid TranslationAO translationAO){
        String word = translationAO.getWord();

        word = word.trim();
        if (word.isEmpty()){
            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        Translation translation = new Translation();
        translation.setWord(translationAO.getWord());
        translation.setTranslation(translationAO.getTranslation());
        translation.setDate(new Date());
        TranslStatistics statistics = translStatisticsService.getStatistics(translation);

        if (ObjectUtils.isEmpty(statistics)){
            return Result.error(new ErrorInfo(ResponseStatusCode.NOT_FOUND.getResultCode(), ResponseStatusCode.NOT_FOUND.getResultMsg()));
        }
        return Result.suc(statistics);
    }

    @ResponseBody
    @PostMapping("/submitStatistics")
    @ApiOperation("提交释义数据")
    public Result submitStatistics(@Valid TranslStatisticsAO statisticsAO, HttpServletRequest req){
        String word = statisticsAO.getWord();
        String translation = statisticsAO.getTranslation();

        word = word.trim();
        if (word.isEmpty()){
            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        HttpSession session = req.getSession();

        if (!ObjectUtils.isEmpty(session.getAttribute("statisticsSet"))){
            HashSet<String> set = (HashSet<String>) session.getAttribute("statisticsSet");
            if (set.contains(translation+"|"+word)){
                return Result.error(new ErrorInfo(ResponseStatusCode.ALREADY_SUBMITTED.getResultCode(), ResponseStatusCode.ALREADY_SUBMITTED.getResultMsg()));
            }

        }else {
            HashSet<String> set = new HashSet<>();
            session.setAttribute("statisticsSet",set);
        }
        if ((statisticsAO.isPopular() + statisticsAO.isOutdated() > 1) || (statisticsAO.isCommendation() + statisticsAO.isNeutral() + statisticsAO.isDerogatory() > 1)){
            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        TranslStatistics translStatistics = new TranslStatistics();
        translStatistics.setWord(statisticsAO.getWord());
        translStatistics.setTranslation(statisticsAO.getTranslation());
        translStatistics.setPopular(statisticsAO.isPopular());
        translStatistics.setOutdated(statisticsAO.isOutdated());
        translStatistics.setCommendation(statisticsAO.isCommendation());
        translStatistics.setDerogatory(statisticsAO.isDerogatory());
        translStatistics.setNeutral(statisticsAO.isNeutral());
        translStatistics.setDate(new Date());
        translStatistics.setWordId(0);

        int flag = translStatisticsService.updateAll(translStatistics);

        if (flag >= 2){
            HashSet<String> set = (HashSet<String>) session.getAttribute("statisticsSet");
            set.add(translation+"|"+word);
            return Result.suc();
        }else {
            return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(),ResponseStatusCode.FAILED.getResultMsg()));
        }
    }

    @ResponseBody
    @PostMapping("/submitFluency")
    @ApiOperation("提交释义数据(流行度)")
    public Result submitStatisticsOfFluency(@Valid TranslStatisticsAO statisticsAO, HttpServletRequest req){
        String word = statisticsAO.getWord();
        String translation = statisticsAO.getTranslation();

        word = word.trim();
        if (word.isEmpty()){
            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        HttpSession session = req.getSession();

        if (!ObjectUtils.isEmpty(session.getAttribute("statisticsSet"))){
            HashSet<String> set = (HashSet<String>) session.getAttribute("statisticsSet");
            if (set.contains(translation+"|"+word+0)){
                return Result.error(new ErrorInfo(ResponseStatusCode.ALREADY_SUBMITTED.getResultCode(), ResponseStatusCode.ALREADY_SUBMITTED.getResultMsg()));
            }

        }else {
            HashSet<String> set = new HashSet<>();
            session.setAttribute("statisticsSet",set);
        }
        if ((statisticsAO.isPopular() + statisticsAO.isOutdated() > 1)){
            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        TranslStatistics translStatistics = new TranslStatistics();
        translStatistics.setWord(statisticsAO.getWord());
        translStatistics.setTranslation(statisticsAO.getTranslation());
        translStatistics.setPopular(statisticsAO.isPopular());
        translStatistics.setOutdated(statisticsAO.isOutdated());
        translStatistics.setDate(new Date());
        translStatistics.setWordId(0);

        int flag = translStatisticsService.updateAll(translStatistics);

        if (flag >= 2){
            HashSet<String> set = (HashSet<String>) session.getAttribute("statisticsSet");
            set.add(translation+"|"+word+0);
            return Result.suc();
        }else {
            return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(),ResponseStatusCode.FAILED.getResultMsg()));
        }
    }

    @ResponseBody
    @PostMapping("/submitPartOfSpeech")
    @ApiOperation("提交释义数据(词性)")
    public Result submitStatisticsOfSpeech(@Valid TranslStatisticsAO statisticsAO, HttpServletRequest req){
        String word = statisticsAO.getWord();
        String translation = statisticsAO.getTranslation();

        word = word.trim();
        if (word.isEmpty()){
            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        HttpSession session = req.getSession();

        if (!ObjectUtils.isEmpty(session.getAttribute("statisticsSet"))){
            HashSet<String> set = (HashSet<String>) session.getAttribute("statisticsSet");
            if (set.contains(translation+"|"+word+1)){
                return Result.error(new ErrorInfo(ResponseStatusCode.ALREADY_SUBMITTED.getResultCode(), ResponseStatusCode.ALREADY_SUBMITTED.getResultMsg()));
            }

        }else {
            HashSet<String> set = new HashSet<>();
            session.setAttribute("statisticsSet",set);
        }
        if ((statisticsAO.isCommendation() + statisticsAO.isNeutral() + statisticsAO.isDerogatory() > 1)){
            return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), ResponseStatusCode.INVALID_PARAMETER.getResultMsg()));
        }

        TranslStatistics translStatistics = new TranslStatistics();
        translStatistics.setWord(statisticsAO.getWord());
        translStatistics.setTranslation(statisticsAO.getTranslation());
        translStatistics.setCommendation(statisticsAO.isCommendation());
        translStatistics.setDerogatory(statisticsAO.isDerogatory());
        translStatistics.setNeutral(statisticsAO.isNeutral());
        translStatistics.setDate(new Date());
        translStatistics.setWordId(0);

        int flag = translStatisticsService.updateAll(translStatistics);

        if (flag >= 2){
            HashSet<String> set = (HashSet<String>) session.getAttribute("statisticsSet");
            set.add(translation+"|"+word+1);
            return Result.suc();
        }else {
            return Result.error(new ErrorInfo(ResponseStatusCode.FAILED.getResultCode(),ResponseStatusCode.FAILED.getResultMsg()));
        }
    }
}
