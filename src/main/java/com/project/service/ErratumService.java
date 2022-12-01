package com.project.service;

import com.project.pojo.Erratum;

public interface ErratumService {
    public int addErratum(Erratum erratum);
    public int deleteErratum(int id);
    public Erratum queryAllErratum();
}
