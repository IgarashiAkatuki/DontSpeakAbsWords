package com.project.service;

import com.project.entity.Source;

import java.util.List;
import java.util.Map;

public interface SourceService {

    // 提交一个出处
    int submitSource(Source source);

    // 查询全部提交的出处
    List<Source> queryAllSource();

    // 查询一个翻译的出处
    Source querySourceByTransl(String translation);

    // 查询一个翻译的出处
    // map中的属性: translation,source
    Source querySourceByName(String translation,String source);

    // 添加一个出处
    int submitSourceToTransl(String translation,String source);

    // 增加出处的提及次数
    int addSourceLike(String translation,String source);

}
