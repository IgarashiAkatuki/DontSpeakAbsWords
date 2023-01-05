package com.project.mapper;

import com.project.entity.Translation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TranslationMapper {

    // ---------------应用在暂存区的方法---------------

    // 获取暂存区中所有的翻译
    List<Translation> queryAllTranslInTemp();

    // 获取暂存区中词条word的翻译
    List<Translation> queryTranslInTempByWord(String word);

    // 给暂存区中的翻译增加点赞
    // map中存储的数据: word,translation
    int addLikeToTemp(Map map);

    // 提交翻译到暂存区
    int addTranslToTemp(Translation translation);

    // 删除暂存区中的翻译
    // map中存储的数据: word,translation
    int deleteTranslInTemp(Map map);

    // 查找暂存区中的翻译
    // map中存储的数据: word,translation
    Translation queryTranslInTemp(Map map);

    // 查找暂存区中翻译的点赞数
    // map中存储的数据: word,translation
    int queryTranslLikeInTemp(Map map);

    // ---------------应用在持久区的方法---------------

    // 查找持久区中词条的翻译
    List<Translation> queryTranslInPSByWord(String word);

    // 向持久区中添加翻译
    int addTranslToPS(Translation translation);

    // 给持久区中的翻译增加点赞
    // map中存储的数据: word,translation
    int addTranslLikeInPS(Map map);

    // 给持久区中的翻译取消点赞
    // map中存储的数据: word,translation
    int deleteTranslLikeInPS(Map map);

    // 在持久区中查找翻译
    // map中存储的数据: word,translation
    Translation queryTranslInPS(Map map);

    // 删除持久区中的翻译
    // map中存储的数据: word,translation
    int deleteTranslInPS(Map map);

    // 更新持久区中的翻译
    // map中存储的数据: word,newTranslation,oldTranslation
    int updateTranslInPS(Map map);

    // 模糊查询
    List<Translation> fuzzyQueryInPS(String word);


}
