package com.midsummra.mapper;

import com.midsummra.pojo.Translation;

import java.util.List;
import java.util.Map;

public interface TranslationMapper {
//    暂存区方法
     List<Translation> queryTranslationFromTemp(String word);
     int addLikesToTemp(String translation);
     int addTranslationToTemp(Translation translation);
     int deleteTranslationByName(String translation);
     Translation queryTranslationByTranslationInTemp(String translation);
     int queryTranslationLikes(String translation);

//    持久区方法

     List<Translation> queryTranslationFromPersistence(String word);
     int addTranslationToPersistence(Translation translation);
     int addLikesToPersistence(String translation);
     int removeLikesInPersistence(String translation);
     Translation queryTranslationByTranslationInPersistence(String translation);
     int addSource(Map map);

}
