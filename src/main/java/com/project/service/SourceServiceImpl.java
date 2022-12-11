package com.project.service;

import com.project.entity.Source;
import com.project.mapper.SourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SourceServiceImpl implements SourceService{

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
    public Source querySourceByName(String translation,String source){

        HashMap<String, String> map = new HashMap<>();
        map.put("translation",translation);
        map.put("source",source);

        return sourceMapper.querySourceByName(map);
    }

    @Override
    // 声明式事务注解
    @Transactional
    public int submitSourceToTransl(String translation,String source) {

        HashMap<String, String> map = new HashMap<>();
        map.put("translation",translation);
        map.put("source",source);

        int flag1 = sourceMapper.submitSourceInSource(map);
        int flag2 = sourceMapper.submitSourceInPS(map);

        if ((flag1 == 1) && (flag2 == 1)){
            return 1;
        }else {
            return 0;
        }

    }

    @Override
    public int addSourceLike(String translation,String source) {

        HashMap<String, String> map = new HashMap<>();
        map.put("translation",translation);
        map.put("source",source);

        return sourceMapper.addSourceLike(map);
    }
}
