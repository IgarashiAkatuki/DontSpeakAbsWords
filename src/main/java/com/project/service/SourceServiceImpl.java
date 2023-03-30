package com.project.service;

import com.project.common.utils.RegexUtils;
import com.project.entity.mysql.Source;
import com.project.mapper.SourceMapper;
import com.project.service.serviceInterface.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class SourceServiceImpl implements SourceService {

    @Autowired
    @Qualifier("sourceMapper")
    private SourceMapper sourceMapper;

    @Override
    public int submitSource(Source source) {
        return sourceMapper.submitSource(source);
    }

    @Override
    public List<Source> queryAllSource() {
        return sourceMapper.queryAllSource();
    }

    @Override
    public Source querySourceByTransl(String translation) {
        return sourceMapper.querySourceByTransl(translation);
    }

    @Override
    public Source querySourceByName(String translation, String source, String url){

        url = RegexUtils.urlRegex(url);

        HashMap<String, String> map = new HashMap<>();
        map.put("translation",translation);
        map.put("source",source);
        map.put("url",url);

        return sourceMapper.querySourceByName(map);
    }

    @Override
    // 声明式事务注解
    @Transactional
    public int submitSourceToTransl(String translation, String source, String url) {



        HashMap<String, String> map = new HashMap<>();
        map.put("translation",translation);
        map.put("source",source);
        map.put("url",url);

        int flag1 = sourceMapper.submitSourceInSource(map);


        HashMap<String, String> map1 = new HashMap<>();
        map1.put("translation",translation);
        map1.put("url",url);
        map1.put("source",source);
        int flag2 = sourceMapper.submitSourceInPS(map1);

        if ((flag1 == 1) && (flag2 == 1)){
            return 1;
        }else {
            return 0;
        }

    }

    @Override
    public int addSourceLike(String translation, String source, String url) {

        url = RegexUtils.urlRegex(url);

        HashMap<String, String> map = new HashMap<>();
        map.put("translation",translation);
        map.put("source",source);
        map.put("url",url);

        return sourceMapper.addSourceLike(map);
    }

    @Override
    public int isInPS(String translation) {
        return sourceMapper.isInPS(translation);
    }
}
