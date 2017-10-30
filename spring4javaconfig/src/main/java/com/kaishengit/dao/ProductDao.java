package com.kaishengit.dao;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
//@Scope("prototype")
//@Lazy
public class ProductDao {

    public void save() {
        System.out.println("productDao save method");
    }
}
