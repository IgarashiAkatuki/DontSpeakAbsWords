package com.project.mapper;

import com.project.entity.mysql.Translation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface PageHelperMapper {

    // 获取总词条数
    public int getTotalRowsInPS(String word);

    public int getTotalRowsInPSAlphabet(String word);

    // 模糊查询
    public List<Translation> fuzzyQueryInPS(Map map);

    public List<Translation> fuzzyQueryInPS4Alphabet(Map map);
}
