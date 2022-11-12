package com.midsummra.mapper;

import com.midsummra.pojo.Erratum;

import java.util.Map;

public interface ErratumMapper {
    public int addErratum(Erratum erratum);
    public int deleteErratum(int id);
}
