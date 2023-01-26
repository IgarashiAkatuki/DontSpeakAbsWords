package com.project.mapper;

import com.project.entity.Source;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SourceMapper {

    // 提交一个出处
    int submitSource(Source source);

    // 查询全部提交的出处
    List<Source> queryAllSource();

    // 通过translation查询一个翻译的出处
    Source querySourceByTransl(String translation);

    // 查询一个翻译的出处
    // map中的属性: translation, source, url
    Source querySourceByName(Map map);

    // 添加一个出处
    // map中的属性: translation, source, url
    int submitSourceInSource(Map map);
    int submitSourceInPS(Map map);

    // 增加出处的提及次数
    // map中存储的数据: translation, source, url
    int addSourceLike(Map map);

}
