package com.project.service;

import com.project.entity.TranslStatistics;
import com.project.entity.Translation;
import com.project.mapper.TranslStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "translationCache")
public class TranslStatisticsServiceImpl implements TranslStatisticsService{

    @Autowired
    @Qualifier("translStatisticsMapper")
    private TranslStatisticsMapper mapper;

    @Override
    public TranslStatistics getStatistics(Translation translation) {
        int flag = mapper.isExist(translation);
        if (flag <= 0){
            mapper.createRows(translation);
        }
        return mapper.getStatistics(translation);
    }

    @Override
    public TranslStatistics getFluency(Translation translation) {
        return mapper.getFluency(translation);
    }

    @Override
    public TranslStatistics getPartOfSpeech(Translation translation) {
        return mapper.getPartOfSpeech(translation);
    }

    @Override
    public int updateFluency(TranslStatistics translStatistics) {
        return mapper.updateFluency(translStatistics);
    }

    @Override
    public int updatePartOfSpeech(TranslStatistics translStatistics) {
        return mapper.updatePartOfSpeech(translStatistics);
    }

    @Override
    public int updateAll(TranslStatistics translStatistics) {
        return mapper.updateFluency(translStatistics) + mapper.updatePartOfSpeech(translStatistics);
    }

    @Override
    public int persistentData() {
        return mapper.persistentData();
    }
}
