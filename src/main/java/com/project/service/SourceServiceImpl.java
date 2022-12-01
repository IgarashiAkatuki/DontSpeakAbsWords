package com.project.service;

import com.project.mapper.SourceMapper;
import com.project.pojo.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SourceServiceImpl implements SourceService{

    @Autowired
    @Qualifier("sourceMapper")
    private SourceMapper sourceMapper;


    @Override
    public int addSource(Source source) {
        return sourceMapper.addSource(source);
    }

    @Override
    public List<Source> queryAllSource() {
        return sourceMapper.queryAllSource();
    }

    @Override
    public int addSourceLikes(String translation,String source) {

        HashMap<String, String> map = new HashMap<>();
        map.put("translation",translation);
        map.put("source",source);

        return sourceMapper.addSourceLikes(map);
    }

    public Source querySource(String translation){
        return sourceMapper.querySource(translation);
    }
}
