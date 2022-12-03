package com.project.service;

import com.project.mapper.ErratumMapper;
import com.project.pojo.Erratum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ErratumServiceImpl implements ErratumService{
    @Autowired
    @Qualifier("erratumMapper")
    private ErratumMapper erratumMapper;

    @Override
    public int addErratum(Erratum erratum) {
        return erratumMapper.addErratum(erratum);
    }

    @Override
    public int deleteErratum(int id) {
        return erratumMapper.deleteErratum(id);
    }

    @Override
    public List<Erratum> queryAllErratum() {
        return erratumMapper.queryAllErratum();
    }
}
