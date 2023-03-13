package com.project.mapper;

import com.project.entity.mysql.TranslStatistics;
import com.project.entity.mysql.Translation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TranslStatisticsMapper {

    // 翻译数据统计

    // 获取所有数据
    TranslStatistics getStatistics(Translation translation);

    // 创建列
    int createRows(Translation translation);

    // 是否存在列
    int isExist(Translation translation);

    // 获取翻译流行度
    TranslStatistics getFluency(Translation translation);

    // 获取翻译词性
    TranslStatistics getPartOfSpeech(Translation translation);

    // 更新流行度
    int updateFluency(TranslStatistics translStatistics);

    // 更新词性
    int updatePartOfSpeech(TranslStatistics translStatistics);


    // 持久化当日数据
    int persistentData();
}
