package com.project.service;

import com.project.constant.Constant;
import com.project.entity.mysql.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletContext;
import java.util.Date;

@Service
public class ScheduledService {

    @Autowired
    private ServletContext context;

    @Autowired
    @Qualifier("statisticsServiceImpl")
    private StatisticsService statisticsService;

    @Autowired
    @Qualifier("translStatisticsServiceImpl")
    private TranslStatisticsService translStatisticsService;


    @Autowired
    @Qualifier("constant")
    private Constant constant;

    // 秒 分 时 日 月 星期 年
    // 一天四次存储数据
    @Scheduled(cron = "0 0 0,6,12,18 * * ?")
    @ConditionalOnProperty(
            name =   "config.enableStatistic",
            havingValue = "true"
    )
    void saveAccessData(){
        Object selectTranslCount = context.getAttribute("selectTranslCount");
        Object addTranslCount = context.getAttribute("addTranslCount");
        Object QuestionnaireCount = context.getAttribute("QuestionnaireCount");
        Object addWordCount = context.getAttribute("addWordCount");

        if ((!ObjectUtils.isEmpty("selectTranslCount"))&&(!ObjectUtils.isEmpty("addTranslCount"))&&(!ObjectUtils.isEmpty("QuestionnaireCount"))&&(!ObjectUtils.isEmpty("addWordCount"))){
            try {
                Date date = new Date();
                Statistics statistics = new Statistics();

                statistics.setDate(date);
                statistics.setAddTranslCount((int)addTranslCount);
                statistics.setAddWordCount((int)addWordCount);
                statistics.setQuestionnaireCount((int)QuestionnaireCount);
                statistics.setSelectTranslCount((int)selectTranslCount);

                int i = statisticsService.addStatistic(statistics);
                if (i == 1){
                    System.out.println("["+date+"]网站访问数据存储成功");
                }else {
                    System.out.println("["+date+"]网站访问数据存储失败");
                }
            }catch (Exception e){
                System.out.println("发生未知错误");
            }

        }


        context.setAttribute("selectTranslCount",0);
        context.setAttribute("addTranslCount",0);
        context.setAttribute("QuestionnaireCount",0);
        context.setAttribute("addWordCount",0);
    }

    @Scheduled(cron = "0 0 4 * * ?")
    @ConditionalOnProperty(
            name =   "config.enableStatistic",
            havingValue = "true"
    )
    void saveTranslStatistics(){
        Date date = new Date();
        int flag = translStatisticsService.persistentData();
        if (flag >= 1){
            System.out.println("["+date+"]释义统计数据存储成功");
        }else {
            System.out.println("["+date+"]释义统计数据存储失败");
        }
    }
}