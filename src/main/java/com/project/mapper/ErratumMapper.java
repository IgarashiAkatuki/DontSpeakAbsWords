package com.project.mapper;

import com.project.pojo.Erratum;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ErratumMapper {
    //
    int addErratum(Erratum erratum);
    int deleteErratum(int id);
    List<Erratum> queryAllErratum();
}
