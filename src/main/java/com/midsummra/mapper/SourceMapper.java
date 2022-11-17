package com.midsummra.mapper;

import com.midsummra.pojo.Source;

import java.util.List;
import java.util.Map;

public interface SourceMapper {

    public int addResource(Source resource);
    public List<Source> queryAllResource();
    public int addResourceLikes(Map map);

}
