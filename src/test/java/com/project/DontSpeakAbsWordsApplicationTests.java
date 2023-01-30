package com.project;

import com.project.constant.Constant;
import com.project.service.TranslStatisticsService;
import com.project.service.TranslationService;
import com.project.service.WordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
class DontSpeakAbsWordsApplicationTests {


    @Autowired
    Constant constant;
    @Autowired
    WordService wordService;

    @Autowired
    TranslationService translationService;

    @Autowired
    TranslStatisticsService statisticsService;


    @Test
    void contextLoads() {
        statisticsService.persistentData();
    }


}
