package com.kaishengit.service.impl;

import com.kaishengit.dao.ProductDao;
import com.kaishengit.entity.Product;
import com.kaishengit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service

public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    @Transactional(rollbackFor = Exception.class,
            isolation = Isolation.SERIALIZABLE,
    propagation = Propagation.REQUIRED,
    readOnly = false)
    public void save(Product product) throws Exception {
        productDao.save(product);

        productDao.save(product);
    }
}
