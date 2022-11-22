package com.midsummra.mapper;

import com.midsummra.pojo.Erratum;

import java.util.Map;

public interface ErratumMapper {
    //
     int addErratum(Erratum erratum);
     int deleteErratum(int id);
     Erratum queryAllErratum();
}
