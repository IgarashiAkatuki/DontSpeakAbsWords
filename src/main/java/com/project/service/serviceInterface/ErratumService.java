package com.project.service.serviceInterface;

import com.project.entity.mysql.Erratum;

import java.util.List;


public interface ErratumService {

    // 添加勘误
    int addErratum(Erratum erratum);

    // 通过id删除勘误
    int deleteErratumById(int id);

    // 通过翻译删除勘误
    int deleteErratumByTransl(String translation);

    // 查询全部勘误
    List<Erratum> queryAllErratum();


}
