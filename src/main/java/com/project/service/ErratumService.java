package com.project.service;

import com.project.pojo.Erratum;

import java.util.List;

public interface ErratumService {
    public int addErratum(Erratum erratum);
    public int deleteErratum(int id);
    public List<Erratum> queryAllErratum();
    public int deleteTranslationErratum(String translation);
}
