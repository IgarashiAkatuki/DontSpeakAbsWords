package com.project.service;

import com.project.entity.Translation;
import com.project.mapper.TranslationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;

@Service
@CacheConfig(cacheNames = "translationCache")
public class TranslationServiceImpl implements TranslationService{

    @Autowired
    @Qualifier("translationMapper")
    private TranslationMapper translationMapper;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate template;

    @Autowired
    @Qualifier("cacheService")
    private CacheService cacheService;

    @Override
    @Cacheable
    public List<Translation> queryAllTranslInTemp() {
        return translationMapper.queryAllTranslInTemp();
    }

    @Override
    @Cacheable
    public List<Translation> queryTranslInTempByWord(String word) {
        return translationMapper.queryTranslInTempByWord(word);
    }

    @Override
//    @CachePut(keyGenerator = "plainKeyGenerator")
    public int addLikeToTemp(String word, String translation) {

        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);

        return translationMapper.addLikeToTemp(map);
    }

    @Override
//    @CachePut(keyGenerator = "plainKeyGenerator")
    public int addTranslToTemp(Translation translation) {
        return translationMapper.addTranslToTemp(translation);
    }

    @Override
//    @CacheEvict(keyGenerator = "plainKeyGenerator")
    public int deleteTranslInTemp(String word, String translation) {

        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);

        return translationMapper.deleteTranslInTemp(map);
    }

    @Override
//    @Cacheable(keyGenerator = "plainKeyGenerator")
    public Translation queryTranslInTemp(String word, String translation) {

        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);

        return translationMapper.queryTranslInTemp(map);
    }

    @Override
//    @Cacheable(keyGenerator = "plainKeyGenerator")
    public int queryTranslLikeInTemp(String word, String translation) {

        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);

        return translationMapper.queryTranslLikeInTemp(map);
    }

    @Override
    @Cacheable
    public List<Translation> queryTranslInPSByWord(String word) {
        return translationMapper.queryTranslInPSByWord(word);
    }

    @Override
//    @CachePut(keyGenerator = "plainKeyGenerator")
    public int addTranslToPS(Translation translation) {
        return translationMapper.addTranslToPS(translation);
    }

    @Override
//    @CachePut(keyGenerator = "plainKeyGenerator")
    public int addTranslLikeInPS(String word, String translation) {

        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);

        return translationMapper.addTranslLikeInPS(map);
    }

    @Override
//    @CacheEvict(keyGenerator = "plainKeyGenerator")
    public int deleteTranslLikeInPS(String word, String translation) {

        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);

        return translationMapper.deleteTranslLikeInPS(map);
    }

    @Override
//    @Cacheable(keyGenerator = "plainKeyGenerator")
    public Translation queryTranslInPS(String word, String translation) {

        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);

        return translationMapper.queryTranslInPS(map);
    }

    @Override
//    @CacheEvict(keyGenerator = "plainKeyGenerator")
    public int deleteTranslInPS(String word, String translation) {

        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("translation",translation);

        return translationMapper.deleteTranslInPS(map);
    }

    @Override
//    @CachePut(keyGenerator = "plainKeyGenerator")
    public int updateTranslInPS(String word, String newTranslation, String oldTranslation) {

        HashMap<String, String> map = new HashMap<>();
        map.put("word",word);
        map.put("newTranslation",newTranslation);
        map.put("oldTranslation",oldTranslation);

        return translationMapper.updateTranslInPS(map);
    }

    @Override
    @Cacheable(keyGenerator = "translKeyGenerator")
    public List<Translation> fuzzyQueryInPS(String word) {

        for (int i = 0; i < word.length(); i++) {
            char alphabetic = word.charAt(i);
            if ((alphabetic >= 'a' && alphabetic <= 'z') || (alphabetic >= 'A' && alphabetic <= 'Z') || Character.isDigit(alphabetic)){
                continue;
            }else {
                StringBuilder sb = new StringBuilder();
                sb.append("_");
                for (int j = 0; j < word.length(); j++) {
                    Object temp = template.opsForValue().get(word.charAt(j)+"");
                    if (ObjectUtils.isEmpty(temp)){
                        sb.append(word.charAt(j));
                    }else {
                        sb.append((String) temp);
                        sb.append("_");
                    }
                }

                if (sb.charAt(sb.length()-1) != '_'){
                    sb.append('_');
                }

                String fuzzyWord = "%"+sb+"%";
                return translationMapper.fuzzyQueryInPS(fuzzyWord);
            }
        }

        String fuzzyWord = "%"+word+"%";
        return translationMapper.fuzzyQueryInPS4Alphabet(fuzzyWord);
    }
}
