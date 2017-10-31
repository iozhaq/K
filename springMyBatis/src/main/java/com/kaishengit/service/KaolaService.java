package com.kaishengit.service;

import com.kaishengit.entity.Kaola;

public interface KaolaService {

    Kaola findById(Integer id);

    void save(Kaola kaola);

}
