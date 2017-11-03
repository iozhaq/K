package com.kaishengit.service.impl;

import com.github.pagehelper.PageHelper;
import com.kaishengit.entity.Kaola;
import com.kaishengit.entity.KaolaExample;
import com.kaishengit.mapper.KaolaMapper;
import com.kaishengit.service.KaolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KaolaServiceImpl implements KaolaService {

    @Autowired
    private KaolaMapper kaolaMapper;

    @Override
    public Kaola findById(Integer id) {
        return kaolaMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Kaola> findByPageNo(Integer pageNo) {
        PageHelper.startPage(pageNo,10);
        KaolaExample kaolaExample = new KaolaExample();
        return kaolaMapper.selectByExample(kaolaExample);
    }

    @Override
    public void save(Kaola kaola) {
        kaolaMapper.insert(kaola);
    }




}
