package com.project.service.serviceInterface;

import com.project.entity.mysql.Translation;

import java.util.List;

public interface PagerHelperService {

    // 获取总词条数
    public int getTotalRows(String word);

    // 模糊查询
    public List<Translation> fuzzyQuery(String word, String startIndex, String limit);
}
