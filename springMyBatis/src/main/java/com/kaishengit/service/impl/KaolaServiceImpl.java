package com.kaishengit.service.impl;

import com.kaishengit.entity.Kaola;
import com.kaishengit.mapper.KaolaMapper;
import com.kaishengit.service.KaolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KaolaServiceImpl implements KaolaService {

    @Autowired
    private KaolaMapper kaolaMapper;

    @Override
    public Kaola findById(Integer id) {
        return kaolaMapper.selectByPrimaryKey(id);
    }

    @Override
    public void save(Kaola kaola) {
        kaolaMapper.insert(kaola);
    }
}
