package com.midsummra.service;

import com.midsummra.pojo.Translation;

import java.util.List;
import java.util.Map;

public interface TranslationService {
    //    暂存区方法
    List<Translation> queryTranslationFromTemp(String word);

    // 此方法有bug，已停止使用，请用加Fixed后缀的同名方法替代
    @Deprecated
    int addLikesToTemp(String translation);
    int addLikesToTempFixed(String word,String translation);

    int addTranslationToTemp(Translation translation);

    // 此方法有bug，已停止使用，请用加Fixed后缀的同名方法替代
    @Deprecated
    int deleteTranslationByName(String translation);
    int deleteTranslationByNameFixed(String word,String translation);

    // 此方法有bug，已停止使用，请用加Fixed后缀的同名方法替代
    @Deprecated
    Translation queryTranslationByTranslationInTemp(String translation);
    Translation queryTranslationByTranslationInTempFixed(String word,String translation);

    // 此方法有bug，已停止使用，请用加Fixed后缀的同名方法替代
    @Deprecated
    int queryTranslationLikes(String translation);
    int queryTranslationLikesFixed(String word,String translation);

//    持久区方法

    List<Translation> queryTranslationFromPersistence(String word);

    int addTranslationToPersistence(Translation translation);

    // 此方法有bug，已停止使用，请用加Fixed后缀的同名方法替代
    @Deprecated
    int addLikesToPersistence(String translation);
    int addLikesToPersistenceFixed(String word, String translation);

    // 此方法有bug，已停止使用，请用加Fixed后缀的同名方法替代
    @Deprecated
    int removeLikesInPersistence(String translation);
    int removeLikesInPersistenceFixed(String word, String translation);

    // 此方法有bug，已停止使用，请用加Fixed后缀的同名方法替代
    @Deprecated
    Translation queryTranslationByTranslationInPersistence(String translation);
    Translation queryTranslationByTranslationInPersistenceFixed(String word, String translation);

    int addSource(String translation,String source);

}
