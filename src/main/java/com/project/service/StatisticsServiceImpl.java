package com.project.service;

import com.project.entity.mysql.Statistics;
import com.project.mapper.StatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService{

    @Autowired
    @Qualifier("statisticsMapper")
    private StatisticsMapper statisticsMapper;


    @Override
    public int addStatistic(Statistics statistics) {
        return statisticsMapper.addStatistic(statistics);
    }

    @Override
    public int selectQuestionnaireCount(int limit) {
        return statisticsMapper.selectQuestionnaireCount(limit);
    }

    @Override
    public int selectWordCount(int limit) {
        return statisticsMapper.selectWordCount(limit);
    }

    @Override
    public int selectTranslCount(int limit) {
        return statisticsMapper.selectTranslCount(limit);
    }

    @Override
    public int selectSelections(int limit) {
        return statisticsMapper.selectSelections(limit);
    }

    @Override
    public List<Statistics> selectAll(int limit) {
        return statisticsMapper.selectAll(limit);
    }
}
