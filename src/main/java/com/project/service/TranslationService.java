package com.project.service;

import com.project.entity.mysql.Translation;

import java.util.List;

public interface TranslationService {

    // ---------------应用在暂存区的方法---------------

    // 获取暂存区中所有的翻译
    List<Translation> queryAllTranslInTemp();

    // 获取暂存区中所有的翻译
    List<Translation> queryTranslInTempByWord(String word);

    // 给暂存区中的翻译增加点赞
    // map中存储的数据: word,translation
    int addLikeToTemp(String word,String translation);

    // 提交翻译到暂存区
    int addTranslToTemp(Translation translation);

    // 删除暂存区中的翻译
    // map中存储的数据: word,translation
    int deleteTranslInTemp(String word,String translation);

    // 查找暂存区中的翻译
    // map中存储的数据: word,translation
    Translation queryTranslInTemp(String word,String translation);

    // 查找暂存区中翻译的点赞数
    // map中存储的数据: word,translation
    int queryTranslLikeInTemp(String word,String translation);

    // ---------------应用在持久区的方法---------------

    // 查找持久区中词条的翻译
    List<Translation> queryTranslInPSByWord(String word);

    // 向持久区中添加翻译
    int addTranslToPS(Translation translation);

    // 给持久区中的翻译增加点赞
    // map中存储的数据: word,translation
    int addTranslLikeInPS(String word,String translation);

    // 给持久区中的翻译取消点赞
    // map中存储的数据: word,translation
    int deleteTranslLikeInPS(String word,String translation);

    // 在持久区中查找翻译
    // map中存储的数据: word,translation
    Translation queryTranslInPS(String word,String translation);

    // 删除持久区中的翻译
    // map中存储的数据: word,translation
    int deleteTranslInPS(String word,String translation);

    // 更新持久区中的翻译
    // map中存储的数据: word,newTranslation,oldTranslation
    int updateTranslInPS(String word,String newTranslation,String oldTranslation);

    List<Translation> fuzzyQueryInPS(String word);

    int addTranslToPsAdmin(Translation translation);
}
