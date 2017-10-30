package com.kaishengit.dao;

import com.kaishengit.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Product product) {
        String sql = "insert into product(product_name,product_inventory) values(?,?)";
        jdbcTemplate.update(sql,product.getProductName(),product.getProductInventory());//insert update delete
    }

    public Product findById(Long id) {
        String sql = "select * from product where id = ?";
        //return jdbcTemplate.queryForObject(sql,new ProductRowMapper(),id);
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Product.class),id);
    }

    public List<Product> findAll() {
        String sql = "select * from product order by id desc";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    public Long count() {
        String sql = "select count(*) from product";
        return jdbcTemplate.queryForObject(sql,Long.class);
    }

    private class ProductRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet resultSet, int i) throws SQLException {
            Product product = new Product();
            product.setId(resultSet.getLong("id"));
            product.setProductInventory(resultSet.getLong("product_inventory"));
            product.setProductName(resultSet.getString("product_name"));
            return product;
        }
    }


}
