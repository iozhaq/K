package com.kaishengit;

import com.kaishengit.entity.Product;
import com.kaishengit.mapper.ProductMapper;
import com.kaishengit.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBatisInterfaceTestCase {

    private SqlSession sqlSession;

    @Before
    public void init() {
        sqlSession = MyBatisUtil.getSqlSession();
    }

    @After
    public void close() {
        sqlSession.close();
    }

    @Test
    public void save() {
        //SqlSession对象根据接口的class动态创建接口的实现类
        //动态代理模式
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);

        Product product = new Product("草莓味可口可乐",20L);
        int updateRows = productMapper.save(product);
        System.out.println("UPDATEROWS>>>>>>>>>>>>>>>" + updateRows);
        System.out.println("PRIMARYKEY>>>>>>>>>>>>>>>" + product.getId());

        sqlSession.commit();
    }

    @Test
    public void findAll() {
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        List<Product> productList = productMapper.findAll();

        for(Product product : productList) {
            System.out.println(product);
        }
    }

    @Test
    public void findById() {
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        Product product = productMapper.findById(1L);
        System.out.println(product);
    }

    @Test
    public void update() {
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        Product product = productMapper.findById(10L);
        product.setProductInventory(200L);
        productMapper.update(product);

        sqlSession.commit();
    }

    @Test
    public void delete() {
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);
        productMapper.delete(23L);
        sqlSession.commit();
    }

    @Test
    public void page() {
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);

        /*Map<String,Integer> map = new HashMap<>();
        map.put("offset",0);
        map.put("size",3);*/

        //List<Product> productList = productMapper.page(map);
        List<Product> productList = productMapper.page(0,3);
        for(Product product : productList) {
            System.out.println(product);
        }
    }

    @Test
    public void batchSave() {
        ProductMapper productMapper = sqlSession.getMapper(ProductMapper.class);

        Product product1 = new Product("AA",200L);
        Product product2 = new Product("BB",200L);
        Product product3 = new Product("CC",200L);

        List<Product> productList = Arrays.asList(product1,product2,product3);
        productMapper.batchSave(productList);

        sqlSession.commit();
    }

}
