package com.project.service;

import com.project.entity.mysql.Word;

import java.util.List;

public interface WordService {

    // 添加词条
    int addWord(Word word);

    // 用id删除词条
    int deleteWordById(int id);

    // 用词条名删除词条
    int deleteWordByName(String word);


    // 通过id查询词条;
    Word queryWordById(int id);

    // 通过词条名查询词条
    Word queryWordByName(String word);

    // 查询所有词条
    List<Word> queryAllWord();

    // 给词条点赞
    int addWordLike(String word);

    // 获取随机数目的词条
    // map中存储的数据: like, limit
    List<Word> getRandomWords(int like,int limit);

    // 获取词条的id
    int getWordId(String word);
}
