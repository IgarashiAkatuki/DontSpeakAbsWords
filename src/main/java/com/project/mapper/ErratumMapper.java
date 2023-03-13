package com.project.mapper;

import com.project.entity.mysql.Erratum;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ErratumMapper {
    // 添加勘误
    int addErratum(Erratum erratum);

    // 通过id删除勘误
    int deleteErratumById(int id);

    // 通过翻译删除勘误
    int deleteErratumByTransl(String translation);

    // 查询全部勘误
    List<Erratum> queryAllErratum();


}
