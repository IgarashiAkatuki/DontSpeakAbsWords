package com.midsummra.mapper;

import com.midsummra.pojo.Translation;

import java.util.List;
import java.util.Map;

public interface TranslationMapper {
//    暂存区方法
    public List<Translation> queryTranslationFromTemp(String word);
    public int addLikesToTemp(String translation);
    public int addTranslationToTemp(Translation translation);
    public int deleteTranslationByName(String translation);
    public Translation queryTranslationByTranslationInTemp(String translation);
    public int queryTranslationLikes(String translation);

//    持久区方法

    public List<Translation> queryTranslationFromPersistence(String word);
    public int addTranslationToPersistence(Translation translation);
    public int addLikesToPersistence(String translation);
    public int removeLikesInPersistence(String translation);
    public Translation queryTranslationByTranslationInPersistence(String translation);
    public int addSource(Map map);

}
