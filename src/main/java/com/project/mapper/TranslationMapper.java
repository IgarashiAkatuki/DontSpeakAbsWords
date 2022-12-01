package com.project.mapper;

import com.project.pojo.Translation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TranslationMapper {
    //    暂存区方法
    List<Translation> queryTranslationFromTemp(String word);

    // 此方法有bug，已停止使用，请用加Fixed后缀的同名方法替代
    @Deprecated
    int addLikesToTemp(String translation);
    int addLikesToTempFixed(Map map);

    int addTranslationToTemp(Translation translation);

    // 此方法有bug，已停止使用，请用加Fixed后缀的同名方法替代
    @Deprecated
    int deleteTranslationByName(String translation);
    int deleteTranslationByNameFixed(Map map);

    // 此方法有bug，已停止使用，请用加Fixed后缀的同名方法替代
    @Deprecated
    Translation queryTranslationByTranslationInTemp(String translation);
    Translation queryTranslationByTranslationInTempFixed(Map map);

    // 此方法有bug，已停止使用，请用加Fixed后缀的同名方法替代
    @Deprecated
    int queryTranslationLikes(String translation);
    int queryTranslationLikesFixed(Map map);

//    持久区方法

    List<Translation> queryTranslationFromPersistence(String word);

    int addTranslationToPersistence(Translation translation);

    // 此方法有bug，已停止使用，请用加Fixed后缀的同名方法替代
    @Deprecated
    int addLikesToPersistence(String translation);
    int addLikesToPersistenceFixed(Map map);

    // 此方法有bug，已停止使用，请用加Fixed后缀的同名方法替代
    @Deprecated
    int removeLikesInPersistence(String translation);
    int removeLikesInPersistenceFixed(Map map);

    // 此方法有bug，已停止使用，请用加Fixed后缀的同名方法替代
    @Deprecated
    Translation queryTranslationByTranslationInPersistence(String translation);
    Translation queryTranslationByTranslationInPersistenceFixed(Map map);

    int addSource(Map map);
}
