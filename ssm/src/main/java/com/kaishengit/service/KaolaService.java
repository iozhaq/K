package com.kaishengit.service;

import com.kaishengit.entity.Kaola;

import java.util.List;

public interface KaolaService {

    Kaola findById(Integer id);

    List<Kaola> findByPageNo(Integer pageNo);

    void save(Kaola kaola);

}
