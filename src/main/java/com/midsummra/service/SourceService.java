package com.midsummra.service;

import com.midsummra.pojo.Source;

import java.util.List;
import java.util.Map;

public interface SourceService {

    public int addSource(Source resource);
    public List<Source> queryAllSource();
    public int addSourceLikes(String translation,String source);
    public Source querySource(String translation);

}
