package com.midsummra.service;

import com.midsummra.mapper.SourceMapper;
import com.midsummra.pojo.Source;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SourceServiceImpl implements SourceService{

    private SourceMapper sourceMapper;

    public void setSourceMapper(SourceMapper sourceMapper) {
        this.sourceMapper = sourceMapper;
    }

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
