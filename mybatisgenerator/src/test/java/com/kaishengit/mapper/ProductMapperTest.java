package com.kaishengit.mapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Product;
import com.kaishengit.entity.ProductExample;
import com.kaishengit.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ProductMapperTest {

    private SqlSession sqlSession;
    private ProductMapper productMapper;

    @Before
    public void init() {
        sqlSession = MyBatisUtil.getSqlSession();
        productMapper = sqlSession.getMapper(ProductMapper.class);
    }

    @After
    public void close() {
        sqlSession.close();
    }

    @Test
    public void insert() {
        Product product = new Product();
        product.setProductName("德芙巧克力");
        product.setProductInventory(100L);

        //productMapper.insert(product);
        productMapper.insertSelective(product);
        sqlSession.commit();
    }

    @Test
    public void findById() {
        Product product = productMapper.selectByPrimaryKey(1L);
        System.out.println(product);
    }

    @Test
    public void findByProductNum() {
        /*ProductExample productExample = new ProductExample();

        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andProductInventoryEqualTo(100L);
        criteria.andProductNameLike("%巧克力%");*/

        ProductExample productExample = new ProductExample();
        productExample.createCriteria()
                .andProductInventoryEqualTo(100L)
                .andProductNameLike("%巧克力%");

        List<Product> productList = productMapper.selectByExample(productExample);
        for(Product product : productList) {
            System.out.println(product);
        }
    }


    @Test
    public void findAll() {
        List<Product> productList = productMapper.selectByExample(new ProductExample());
        for(Product product : productList) {
            System.out.println(product);
        }
    }

    @Test
    public void deleteByExample() {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andProductInventoryEqualTo(20L);

        productMapper.deleteByExample(productExample);
        sqlSession.commit();
    }

    @Test
    public void updateByExample() {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andProductInventoryEqualTo(100L);

        Product product = new Product();
        product.setProductInventory(150L);

        productMapper.updateByExampleSelective(product,productExample);
        sqlSession.commit();
    }

    @Test
    public void findByOr() {
        ProductExample productExample = new ProductExample();
        productExample.or().andProductNameLike("%巧克力%");
        productExample.or().andProductNameLike("%可口可乐%");
        productExample.setOrderByClause("id desc");

        List<Product> productList = productMapper.selectByExample(productExample);
        for(Product product : productList) {
            System.out.println(product);
        }
    }

    @Test
    public void page() {
        //PageHelper.startPage(2,5);

        PageHelper.offsetPage(5,5);

        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andProductInventoryGreaterThan(10L);

        List<Product> productList = productMapper.selectByExample(productExample);
        for(Product product : productList) {
            System.out.println(product);
        }

        PageInfo<Product> pageInfo = new PageInfo<>(productList);
        System.out.println("Pages>>>>>>>" + pageInfo.getPages());
    }

    @Test
    public void page2() {
        List<Product> productList = productMapper.page(5,2);
        for(Product product : productList) {
            System.out.println(product);
        }
    }
}
