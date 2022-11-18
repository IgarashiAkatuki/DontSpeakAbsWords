package com.midsummra.mapper;

import com.midsummra.pojo.Source;

import java.util.List;
import java.util.Map;

public interface SourceMapper {

    public int addSource(Source resource);
    public List<Source> queryAllSource();
    public int addSourceLikes(Map map);
    public Source querySource(String translation);

}
