package com.kaishengit.dao;


import com.kaishengit.BaseTest;
import com.kaishengit.entity.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void save() {
        Product product = new Product();
        product.setProductName("Spring4");
        product.setProductInventory(10L);

        productDao.save(product);
    }

    @Test
    public void findById() {
        Product product = productDao.findById(1L);
        System.out.println(product);
    }

    @Test
    public void findAll() {
        List<Product> productList = productDao.findAll();
        for(Product product : productList) {
            System.out.println(product);
        }
    }

    @Test
    public void count() {
        System.out.println(productDao.count());
    }

}
