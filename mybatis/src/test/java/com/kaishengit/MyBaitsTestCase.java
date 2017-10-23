package com.kaishengit;

import com.kaishengit.entity.Product;
import com.kaishengit.util.MyBatisUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.Reader;
import java.util.List;

public class MyBaitsTestCase {

    @Test
    public void save() throws Exception {
        //读取classPath中MyBatis的配置文件
        Reader reader = Resources.getResourceAsReader("mybatis.xml");
        //InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");

        //根据Reader或InputStream来创建SqlSessionFactory对象
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);

        //根据SqlSessionFactory对象来创建SqlSession
        SqlSession sqlSession = sessionFactory.openSession(true);

        Product product = new Product("漫步者音响第二版",100L);

        //执行insert操作
        sqlSession.insert("com.kaishengit.mapper.ProductMapper.save",product);

        //提交事务
        //sqlSession.commit();
        //释放资源
        sqlSession.close();



    }

    @Test
    public void findAll() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();

        List<Product> productList = sqlSession.selectList("com.kaishengit.mapper.ProductMapper.findAll");
        for(Product product : productList) {
            System.out.println(product);
        }
        sqlSession.close();
    }

    @Test
    public void findById() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        Product product = sqlSession.selectOne("com.kaishengit.mapper.ProductMapper.findById",1L);
        System.out.println(product);
        sqlSession.close();
    }

    @Test
    public void update() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        Product product = sqlSession.selectOne("com.kaishengit.mapper.ProductMapper.findById",1L);
        product.setProductInventory(200L);

        sqlSession.update("com.kaishengit.mapper.ProductMapper.update",product);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void delete() {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        sqlSession.delete("com.kaishengit.mapper.ProductMapper.delete",8L);
        sqlSession.commit();
        sqlSession.close();
    }

}
