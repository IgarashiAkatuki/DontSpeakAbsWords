package com.project.mapper;

import com.project.pojo.Source;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SourceMapper {
    int addSource(Source resource);
    List<Source> queryAllSource();
    int addSourceLikes(Map map);
    Source querySource(String translation);
}
