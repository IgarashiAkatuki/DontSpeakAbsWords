package com.midsummra.service;

import com.midsummra.pojo.Erratum;

public interface ErratumService {
    public int addErratum(Erratum erratum);
    public int deleteErratum(int id);
    public Erratum queryAllErratum();
}
