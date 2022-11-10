package com.midsummra.service;

import com.midsummra.mapper.TranslationMapper;
import com.midsummra.pojo.Translation;

import java.util.List;

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
    public int addLikesToTemp(String translation) {
        return translationMapper.addLikesToTemp(translation);
    }

    @Override
    public int addTranslationToTemp(Translation translation) {
        return translationMapper.addTranslationToTemp(translation);
    }

    @Override
    public int deleteTranslationByName(String translation) {
        return translationMapper.deleteTranslationByName(translation);
    }

    @Override
    public Translation queryTranslationByTranslationInTemp(String translation) {
        return translationMapper.queryTranslationByTranslationInTemp(translation);
    }

    @Override
    public int queryTranslationLikes(String translation) {
        return translationMapper.queryTranslationLikes(translation);
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
    public int addLikesToPersistence(String translation) {
        return translationMapper.addLikesToPersistence(translation);
    }

    @Override
    public Translation queryTranslationByTranslationInPersistence(String translation) {
        return translationMapper.queryTranslationByTranslationInPersistence(translation);
    }

}
