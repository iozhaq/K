package com.kaishengit.service.impl;

import com.kaishengit.entity.Kaola;
import com.kaishengit.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class KaolaServiceImplTest extends BaseTest {

    @Autowired
    private KaolaServiceImpl kaolaService;

    @Test
    public void findById() throws Exception {
        Kaola kaola = kaolaService.findById(30906);
        System.out.println(kaola);
    }

    @Test
    public void save() {
        Kaola kaola = new Kaola();
        kaola.setProductName("FREDS 婴幼儿腋下游泳圈 3岁以下 6-18千克 红色 胸围53厘米以下");
        kaola.setMarketPrice(new BigDecimal("199"));
        kaola.setPrice(new BigDecimal("130"));
        kaola.setPlace("德国");
        kaola.setCommentNum(8451);
        kaola.setTypeId(24);

        kaolaService.save(kaola);
        System.out.println(">>>>>>>>>>>>>" + kaola.getId());
    }

}