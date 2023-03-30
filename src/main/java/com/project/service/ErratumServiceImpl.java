package com.project.service;

import com.project.entity.mysql.Erratum;
import com.project.mapper.ErratumMapper;
import com.project.service.serviceInterface.ErratumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ErratumServiceImpl implements ErratumService {

    @Autowired
    @Qualifier("erratumMapper")
    private ErratumMapper erratumMapper;

    @Override
    public int addErratum(Erratum erratum) {
        return erratumMapper.addErratum(erratum);
    }

    @Override
    public int deleteErratumById(int id) {
        return erratumMapper.deleteErratumById(id);
    }

    @Override
    public int deleteErratumByTransl(String translation) {
        return erratumMapper.deleteErratumByTransl(translation);
    }

    @Override
    public List<Erratum> queryAllErratum() {
        return erratumMapper.queryAllErratum();
    }
}
