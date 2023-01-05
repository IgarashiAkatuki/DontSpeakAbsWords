package com.project;

import com.project.constant.Constant;
import com.project.entity.Word;
import com.project.service.Hanzi2EmojiService;
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


    @Test
    void contextLoads() {
    }


}
