package com.project.service;

import com.project.entity.Translation;
import com.project.mapper.TranslationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;

@Service
public class TranslationServiceImpl implements TranslationService{

    @Autowired
    @Qualifier("translationMapper")
    private TranslationMapper translationMapper;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate template;

    @Override
    public List<Translation> queryAllTranslInTemp() {
        return translationMapper.queryAllTranslInTemp();
    }

    @Override
    public List<Translation> queryTranslInTempByWord(String word) {
        return translationMapper.queryTranslInTempByWord(word);
    }

    @Override
    public int addLikeToTemp(String word, String translation) {

        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);

        return translationMapper.addLikeToTemp(map);
    }

    @Override
    public int addTranslToTemp(Translation translation) {
        return translationMapper.addTranslToTemp(translation);
    }

    @Override
    public int deleteTranslInTemp(String word, String translation) {

        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);

        return translationMapper.deleteTranslInTemp(map);
    }

    @Override
    public Translation queryTranslInTemp(String word, String translation) {

        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);

        return translationMapper.queryTranslInTemp(map);
    }

    @Override
    public int queryTranslLikeInTemp(String word, String translation) {

        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);

        return translationMapper.queryTranslLikeInTemp(map);
    }

    @Override
    public List<Translation> queryTranslInPSByWord(String word) {
        return translationMapper.queryTranslInPSByWord(word);
    }

    @Override
    public int addTranslToPS(Translation translation) {
        return translationMapper.addTranslToPS(translation);
    }

    @Override
    public int addTranslLikeInPS(String word, String translation) {

        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);

        return translationMapper.addTranslLikeInPS(map);
    }

    @Override
    public int deleteTranslLikeInPS(String word, String translation) {

        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);

        return translationMapper.deleteTranslLikeInPS(map);
    }

    @Override
    public Translation queryTranslInPS(String word, String translation) {

        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);

        return translationMapper.queryTranslInPS(map);
    }

    @Override
    public int deleteTranslInPS(String word, String translation) {

        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);

        return translationMapper.deleteTranslInPS(map);
    }

    @Override
    public int updateTranslInPS(String word, String newTranslation, String oldTranslation) {

        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("newTranslation",newTranslation);
        map.put("oldTranslation",oldTranslation);

        return translationMapper.updateTranslInPS(map);
    }

    @Override
    public List<Translation> fuzzyQueryInPS(String word) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            Object temp = template.opsForValue().get(word.charAt(i)+"");
            if (ObjectUtils.isEmpty(temp)){
                sb.append(word.charAt(i));
            }else {
                sb.append((String) temp);
                sb.append("_");
            }
        }

        if (sb.charAt(sb.length()-1) == '_'){
            sb.delete(sb.length()-1,sb.length());
        }

        String fuzzyWord = "%"+sb+"%";
        return translationMapper.fuzzyQueryInPS(fuzzyWord);
    }
}
