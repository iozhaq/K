package com.kaishengit.hibernate;

import com.kaishengit.pojo.Product;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.Test;

import java.util.List;

public class HelloWorld {

    @Test
    public void save() {
        //1. 读取classpath中名称为hibernate.cfg.xml的配置文件
        Configuration configuration = new Configuration().configure();
        //2. 创建SessionFactory
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        //3. 创建Session
        Session session = sessionFactory.getCurrentSession();
        //4. 创建事务
        Transaction transaction = session.beginTransaction();
        //5. 执行操作
        Product product = new Product();
        product.setProductName("HDMI转接器");
        product.setProductInventory(100);

        session.save(product);

        //6. 提交或回滚事务
        transaction.commit();
        //7. 释放资源
        //session.close();


    }

    @Test
    public void findById() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        //.,...
        Product product = (Product) session.get(Product.class,2);
        System.out.println(product);

        session.getTransaction().commit();
    }

    @Test
    public void update() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Product product = (Product) session.get(Product.class,2);
        product.setProductInventory(150);

        session.getTransaction().commit();
    }

    @Test
    public void delete() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Product product = (Product) session.get(Product.class,2);
        session.delete(product);

        session.getTransaction().commit();
    }

    @Test
    public void findAll() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        //HQL
        String hql = "from Product order by id desc ";
        Query query = session.createQuery(hql);
        List<Product> productList = query.list();

        for(Product product : productList) {
            System.out.println(product);
        }

        session.getTransaction().commit();
    }

    @Test
    public void get() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Product product = (Product) session.get(Product.class,399);
        System.out.println(product == null);

        session.getTransaction().commit();
    }

    @Test
    public void load() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Product product = (Product) session.load(Product.class,3);



        session.getTransaction().commit();
        System.out.println(product);

    }

    @Test
    public void saveToDb() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Product product = new Product();
        product.setProductName("雷蛇机械键盘X99");
        product.setProductInventory(100);

        session.persist(product);
        System.out.println("ID:" + product.getId());

        session.getTransaction().commit();
    }

    @Test
    public void updateMethod() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Product product = (Product) session.get(Product.class,43);
        System.out.println(product);

        session.getTransaction().commit();

        Session session2 = HibernateUtil.getSession();
        session2.beginTransaction();

        product.setProductInventory(300);
        session2.update(product);

        session2.getTransaction().commit();
    }

    @Test
    public void saveOrUpdate() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Product product = new Product();
        product.setProductName("麦克风100-9型");
        product.setProductInventory(100);

        session.saveOrUpdate(product);

        session.getTransaction().commit();

        Session session2 = HibernateUtil.getSession();
        session2.beginTransaction();

        product.setProductInventory(250);
        session2.saveOrUpdate(product);

        session2.getTransaction().commit();

    }

    @Test
    public void merge() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Product product = (Product) session.get(Product.class,49);

        session.getTransaction().commit();

        Session session2 = HibernateUtil.getSession();
        session2.beginTransaction();

        product.setProductInventory(250);
        session2.merge(product);

        session2.getTransaction().commit();

    }

    @Test
    public void clear() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Product product = (Product) session.get(Product.class,49);

        session.clear();

        product.setProductInventory(300);

        session.getTransaction().commit();
    }

    @Test
    public void flush() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Product product = (Product) session.get(Product.class,49);
        product.setProductInventory(350);

        session.flush();

        session.getTransaction().commit();
    }


}
