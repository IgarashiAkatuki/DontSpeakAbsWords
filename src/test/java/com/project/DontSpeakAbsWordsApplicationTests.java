package com.project;

import com.project.constant.Constant;
import com.project.entity.Word;
import com.project.service.TranslationService;
import com.project.service.WordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

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
        Word word = new Word();
        word.setWord("114");
        word.setDate(new Date());
        word.setLikes(1);
        wordService.addWord(word);
    }

}
