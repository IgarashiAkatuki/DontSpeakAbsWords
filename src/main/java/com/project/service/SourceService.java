package com.project.service;

import com.project.pojo.Source;

import java.util.List;

public interface SourceService {
    public int addSource(Source resource);
    public List<Source> queryAllSource();
    public int addSourceLikes(String translation,String source);
    public Source querySource(String translation);
    int submitSource(String translation,String source);

}
