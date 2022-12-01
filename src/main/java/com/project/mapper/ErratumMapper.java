package com.project.mapper;

import com.project.pojo.Erratum;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ErratumMapper {
    //
    int addErratum(Erratum erratum);
    int deleteErratum(int id);
    Erratum queryAllErratum();
}
