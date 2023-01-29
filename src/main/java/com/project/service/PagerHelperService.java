package com.project.service;

import com.project.entity.Translation;

import java.util.List;
import java.util.Map;

public interface PagerHelperService {

    // 获取总词条数
    public int getTotalRows(String word);

    // 模糊查询
    public List<Translation> fuzzyQuery(String word, String startIndex, String limit);
}
