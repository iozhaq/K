package com.kaishengit.dao;

import com.kaishengit.Application;
import com.kaishengit.service.ProductService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ProductDaoTest {

    @Test
    public void save() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Application.class); //new ClassPathXmlApplicationContext("applicationContext.xml");
        ProductService productService =
                (ProductService) applicationContext.getBean("productServiceImpl");
        productService.save();
    }

}
