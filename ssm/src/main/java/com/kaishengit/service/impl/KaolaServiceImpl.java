package com.kaishengit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Kaola;
import com.kaishengit.entity.KaolaExample;
import com.kaishengit.entity.KaolaType;
import com.kaishengit.entity.KaolaTypeExample;
import com.kaishengit.mapper.KaolaMapper;
import com.kaishengit.mapper.KaolaTypeMapper;
import com.kaishengit.service.KaolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KaolaServiceImpl implements KaolaService {

    @Autowired
    private KaolaMapper kaolaMapper;
    @Autowired
    private KaolaTypeMapper kaolaTypeMapper;

    @Override
    public Kaola findById(Integer id) {
        return kaolaMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<Kaola> findByPageNo(Integer pageNo) {
        PageHelper.startPage(pageNo,10);

        KaolaExample kaolaExample = new KaolaExample();
        kaolaExample.setOrderByClause("id desc");

        List<Kaola> list = kaolaMapper.selectByExample(kaolaExample);
        return new PageInfo<Kaola>(list);
    }

    @Override
    public void save(Kaola kaola) {
        //刚添加的商品，评论数量默认为0
        kaola.setCommentNum(0);

        kaolaMapper.insert(kaola);
    }

    @Override
    public void editKaola(Kaola kaola) {
        kaolaMapper.updateByPrimaryKeySelective(kaola);
    }

    @Override
    public void deleteKaolaById(Integer id) {
        kaolaMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取所有的商品类型
     *
     * @return
     */
    @Override
    public List<KaolaType> findAllType() {
        return kaolaTypeMapper.selectByExample(new KaolaTypeExample());
    }


}
