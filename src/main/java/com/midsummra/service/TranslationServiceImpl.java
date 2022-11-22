package com.midsummra.service;

import com.midsummra.mapper.TranslationMapper;
import com.midsummra.pojo.Translation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranslationServiceImpl implements TranslationService{
    private TranslationMapper translationMapper;

    public void setTranslationMapper(TranslationMapper translationMapper){
        this.translationMapper = translationMapper;
    }

    @Override
    public List<Translation> queryTranslationFromTemp(String word) {
        return translationMapper.queryTranslationFromTemp(word);
    }

    @Override
    @Deprecated
    public int addLikesToTemp(String translation) {
        return translationMapper.addLikesToTemp(translation);
    }

    @Override
    public int addLikesToTempFixed(String word, String translation) {
        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);
        return translationMapper.addLikesToTempFixed(map);
    }

    @Override
    public int addTranslationToTemp(Translation translation) {
        return translationMapper.addTranslationToTemp(translation);
    }

    @Override
    @Deprecated
    public int deleteTranslationByName(String translation) {
        return translationMapper.deleteTranslationByName(translation);
    }

    @Override
    public int deleteTranslationByNameFixed(String word, String translation) {
        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);
        return translationMapper.deleteTranslationByNameFixed(map);
    }

    @Override
    @Deprecated
    public Translation queryTranslationByTranslationInTemp(String translation) {
        return translationMapper.queryTranslationByTranslationInTemp(translation);
    }

    @Override
    public Translation queryTranslationByTranslationInTempFixed(String word, String translation) {
        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);
        return translationMapper.queryTranslationByTranslationInTempFixed(map);
    }

    @Override
    @Deprecated
    public int queryTranslationLikes(String translation) {
        return translationMapper.queryTranslationLikes(translation);
    }

    @Override
    public int queryTranslationLikesFixed(String word, String translation) {
        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);
        return translationMapper.queryTranslationLikesFixed(map);
    }

    @Override
    public List<Translation> queryTranslationFromPersistence(String word) {
        return translationMapper.queryTranslationFromPersistence(word);
    }

    @Override
    public int addTranslationToPersistence(Translation translation) {
        return translationMapper.addTranslationToPersistence(translation);
    }

    @Override
    @Deprecated
    public int addLikesToPersistence(String translation) {
        return translationMapper.addLikesToPersistence(translation);
    }

    @Override
    public int addLikesToPersistenceFixed(String word, String translation) {
        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);
        return translationMapper.addLikesToPersistenceFixed(map);
    }

    @Override
    @Deprecated
    public int removeLikesInPersistence(String translation) {
        return translationMapper.removeLikesInPersistence(translation);
    }

    @Override
    public int removeLikesInPersistenceFixed(String word, String translation) {
        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);
        return translationMapper.removeLikesInPersistenceFixed(map);
    }

    @Override
    @Deprecated
    public Translation queryTranslationByTranslationInPersistence(String translation) {
        return translationMapper.queryTranslationByTranslationInPersistence(translation);
    }

    @Override
    public Translation queryTranslationByTranslationInPersistenceFixed(String word, String translation) {
        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);
        return translationMapper.queryTranslationByTranslationInPersistenceFixed(map);
    }

    @Override
    public int addSource(String translation,String source) {

        HashMap<String, String> map = new HashMap<>();
        map.put("translation",translation);
        map.put("source",source);

        return translationMapper.addSource(map);
    }

}
