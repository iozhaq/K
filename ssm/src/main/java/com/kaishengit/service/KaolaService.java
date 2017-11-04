package com.kaishengit.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Kaola;
import com.kaishengit.entity.KaolaType;

import java.util.List;
import java.util.Map;


public interface KaolaService {

    Kaola findById(Integer id);

    PageInfo<Kaola> findByPageNo(Integer pageNo);
    PageInfo<Kaola> findByPageNo(Integer pageNo,Map<String,Object> queryParam);
    List<String> findProductPlaceList();

    void save(Kaola kaola);

    void editKaola(Kaola kaola);

    void deleteKaolaById(Integer id);

    /**
     * 获取所有的商品类型
     * @return
     */
    List<KaolaType> findAllType();

}
