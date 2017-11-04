package com.kaishengit.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Kaola;
import com.kaishengit.entity.KaolaType;

import java.util.List;


public interface KaolaService {

    Kaola findById(Integer id);

    PageInfo<Kaola> findByPageNo(Integer pageNo);

    void save(Kaola kaola);

    void editKaola(Kaola kaola);

    void deleteKaolaById(Integer id);

    /**
     * 获取所有的商品类型
     * @return
     */
    List<KaolaType> findAllType();

}
