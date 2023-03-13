package com.project.service;

import com.project.entity.mysql.Statistics;

import java.util.List;

public interface StatisticsService {

    // 添加数据
    int addStatistic(Statistics statistics);

    // 搜寻获取问卷数
    int selectQuestionnaireCount(int limit);

    // 搜寻添加的词条数
    int selectWordCount(int limit);

    // 搜寻提交的翻译数
    int selectTranslCount(int limit);

    // 搜寻查询次数
    int selectSelections(int limit);

    List<Statistics> selectAll(int limit);
}
