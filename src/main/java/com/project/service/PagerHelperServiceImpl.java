package com.project.service;

import com.project.entity.mysql.Translation;
import com.project.mapper.PageHelperMapper;
import com.project.service.serviceInterface.PagerHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;

@Service
@SuppressWarnings("rawtypes")
public class PagerHelperServiceImpl implements PagerHelperService {

    @Autowired
    @Qualifier("pageHelperMapper")
    private PageHelperMapper helper;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate template;

    @Override
    public int getTotalRows(String word) {

        for (int i = 0; i < word.length(); i++) {
            char alphabetic = word.charAt(i);
            if ((alphabetic >= 'a' && alphabetic <= 'z') || (alphabetic >= 'A' && alphabetic <= 'Z') || Character.isDigit(alphabetic)){
                continue;
            }else {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < word.length(); j++) {
                    Object temp = template.opsForValue().get(word.charAt(j)+"");
                    if (ObjectUtils.isEmpty(temp)){
                        sb.append(word.charAt(j));
                    }else {
                        sb.append((String) temp);
                        sb.append("_");
                    }
                }

                if (sb.charAt(sb.length()-1) == '_'){
                    sb.delete(sb.length()-1,sb.length());
                }

                String fuzzyWord = "%"+sb+"%";
                return helper.getTotalRowsInPS(fuzzyWord);
            }
        }

        String fuzzyWord = "%"+word+"%";
        return helper.getTotalRowsInPSAlphabet(fuzzyWord);
    }


    @Override
    public List<Translation> fuzzyQuery(String word, String startIndex, String limit) {

        HashMap<String, String> map = new HashMap<>();

        for (int i = 0; i < word.length(); i++) {
            char alphabetic = word.charAt(i);
            if ((alphabetic >= 'a' && alphabetic <= 'z') || (alphabetic >= 'A' && alphabetic <= 'Z') || Character.isDigit(alphabetic)){
                continue;
            }else {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < word.length(); j++) {
                    Object temp = template.opsForValue().get(word.charAt(j)+"");
                    if (ObjectUtils.isEmpty(temp)){
                        sb.append(word.charAt(j));
                    }else {
                        sb.append((String) temp);
                        sb.append("_");
                    }
                }

                if (sb.charAt(sb.length()-1) == '_'){
                    sb.delete(sb.length()-1,sb.length());
                }

                String fuzzyWord = "%"+sb+"%";
                map.put("fuzzyWord",fuzzyWord);
                map.put("limit",limit);
                map.put("startIndex",startIndex);
                return helper.fuzzyQueryInPS(map);
            }
        }

        String fuzzyWord = "%"+word+"%";
        map.put("fuzzyWord",fuzzyWord);
        map.put("limit",limit);
        map.put("startIndex",startIndex);
        return helper.fuzzyQueryInPS4Alphabet(map);
    }
}
