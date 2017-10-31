package com.kaishengit.service.impl;

import com.kaishengit.Application;
import com.kaishengit.entity.Product;
import com.kaishengit.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)//(locations = "classpath:applicationContext.xml")
public class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Test
    public void save() throws Exception {
        Product product = new Product();
        product.setProductName("音响");
        product.setProductInventory(100L);
        productService.save(product);
    }


}
