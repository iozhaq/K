package com.kaishengit.crm.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;

import java.util.List;

/**
 * 客户管理业务层
 * @author fankay
 */
public interface CustomerService {
    /**
     * 我的客户 分页方法
     * @param account 当前登录的account对象
     * @param pageNo 页号
     * @return
     */
    PageInfo<Customer> pageForMyCustomer(Account account, Integer pageNo);

    /**
     * 获取所有客户行业名称
     * @return
     */
    List<String> findAllCustomerTrade();

    /**
     * 获取所有客户来源名称
     * @return
     */
    List<String> findAllCustomerSource();

    /**
     * 添加新客户
     * @param customer
     */
    void saveNewCustomer(Customer customer);

    /**
     * 根据主键查询客户
     * @param id
     * @return
     */
    Customer findCustomerById(Integer id);
}
