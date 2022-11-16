package com.midsummra.service;

import com.midsummra.mapper.ErratumMapper;
import com.midsummra.pojo.Erratum;
import org.springframework.stereotype.Service;

@Service
public class ErratumServiceImpl implements ErratumService{
    private ErratumMapper erratumMapper;

    public void setErratumMapper(ErratumMapper erratumMapper) {
        this.erratumMapper = erratumMapper;
    }

    @Override
    public int addErratum(Erratum erratum) {
        return erratumMapper.addErratum(erratum);
    }

    @Override
    public int deleteErratum(int id) {
        return erratumMapper.deleteErratum(id);
    }

    @Override
    public Erratum queryAllErratum() {
        return erratumMapper.queryAllErratum();
    }
}
