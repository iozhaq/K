package com.kaishengit.mapper;

import com.kaishengit.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductMapper {

    int save(Product product);
    int update(Product product);
    int delete(Long id);
    List<Product> findAll();
    //List<Product> page(Map<String,Integer> map);//limit offset size
    List<Product> page(@Param("offset") int offset,
                       @Param("size") int size);
    Product findById(Long id);

}
