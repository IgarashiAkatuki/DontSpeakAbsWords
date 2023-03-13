package com.project.service;

import com.project.entity.mysql.TranslStatistics;
import com.project.entity.mysql.Translation;

public interface TranslStatisticsService {

    // 翻译数据统计

    // 获取所有数据
    TranslStatistics getStatistics(Translation translation);


    // 获取翻译流行度
    TranslStatistics getFluency(Translation translation);

    // 获取翻译词性
    TranslStatistics getPartOfSpeech(Translation translation);

    // 更新流行度
    int updateFluency(TranslStatistics translStatistics);

    // 更新词性
    int updatePartOfSpeech(TranslStatistics translStatistics);

    // 全部更新
    int updateAll(TranslStatistics translStatistics);

    // 持久化当日数据
    int persistentData();
}
