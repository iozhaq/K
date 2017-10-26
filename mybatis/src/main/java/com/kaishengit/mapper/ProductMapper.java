package com.kaishengit.mapper;

import com.kaishengit.entity.Product;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface ProductMapper {

    @Insert("insert into product(product_name,product_inventory) values(#{productName},#{productInventory})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int save(Product product);
    @Update("UPDATE product " +
            "SET product_name = #{productName}, product_inventory =#{productInventory} " +
            "WHERE " +
            "id = #{id}")
    int update(Product product);
    @Delete("delete from product where id = #{id}")
    int delete(Long id);

    @Select("select * from product")
    @Options(useCache = false)
    List<Product> findAll();
    //List<Product> page(Map<String,Integer> map);//limit offset size

    @Select("select * from product limit #{offset},#{size}")
    List<Product> page(@Param("offset") int offset,
                       @Param("size") int size);

    @Select("select * from product where id = #{id}")
    Product findById(Long id);

    int batchSave(@Param("productList") List<Product> productList);

}
