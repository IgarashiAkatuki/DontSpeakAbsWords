package com.midsummra.mapper;

import com.midsummra.pojo.Source;

import java.util.List;
import java.util.Map;

public interface SourceMapper {

     int addSource(Source resource);
     List<Source> queryAllSource();
     int addSourceLikes(Map map);
     Source querySource(String translation);

}
