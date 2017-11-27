package com.kaishengit.hibernate;

import com.kaishengit.pojo.Product;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class HqlTest {

    private Session session;

    @Before
    public void before() {
        session = HibernateUtil.getSession();
        session.getTransaction().begin();
    }

    @After
    public void after() {
        session.getTransaction().commit();
    }

    @Test
    public void findAll() {
        String hql = "from Product";
        Query query = session.createQuery(hql);
        List<Product> productList = query.list();
        showList(productList);
    }

    @Test
    public void findById() {
        String hql = "from Product where id = ?";
        Query query = session.createQuery(hql);
        query.setParameter(0,49);

        Product product = (Product) query.uniqueResult();
        System.out.println(product);
    }

    @Test
    public void findByName() {
        String hql = "from Product where productName = :name";

        Query query = session.createQuery(hql);
        //query.setParameter("name","德芙巧克力");
        query.setString("name","巧克力");

        List<Product> productList = query.list();
        showList(productList);
    }

    @Test
    public void findSingleNameColumn() {
        String hql = "select productName from Product";
        Query query = session.createQuery(hql);
        List<String> productNameList = query.list();
        for(String name : productNameList) {
            System.out.println(name);
        }
    }

    @Test
    public void findIdAndNameColumn() {
        String hql = "select id,productName,productInventory from Product";
        Query query = session.createQuery(hql);
        List<Object[]> dataList = query.list();
        for(Object[] array : dataList) {
            System.out.println(array[0] + " -> " + array[1] + " -> " + array[2]);
        }
    }

    @Test
    public void count() {
        String hql = "select count(*),max(productInventory) from Product";
        Query query = session.createQuery(hql);
        Object[] data = (Object[]) query.uniqueResult();
        System.out.println(data[0] + " ->  " + data[1]);
    }

    @Test
    public void page() {
        String hql = "from Product order by id desc";
        Query query = session.createQuery(hql);
        //limit 5,5
        query.setFirstResult(5);
        query.setMaxResults(5);

        List<Product> productList = query.list();
        showList(productList);
    }

    private void showList(List<Product> productList) {
        for(Product product : productList) {
            System.out.println(product);
        }
    }

}
