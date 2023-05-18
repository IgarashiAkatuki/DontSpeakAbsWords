package com.project.mapper;

import com.project.entity.mysql.Translation;
import com.project.pojo.PageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PageHelperMapper {

    // 获取总词条数
    public int getTotalRowsInPS(String word);

    public int getTotalRowsInPSAlphabet(String word);

    // 模糊查询
    public List<Translation> fuzzyQueryInPS(PageDTO map);

    public List<Translation> fuzzyQueryInPS4Alphabet(PageDTO map);
}
