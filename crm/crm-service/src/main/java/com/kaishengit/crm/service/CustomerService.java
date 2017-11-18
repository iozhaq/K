package com.kaishengit.crm.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

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

    /**
     * 删除指定的客户
     * @param customer
     */
    void deleteCustomer(Customer customer);

    /**
     * 将指定客户放入公海
     * @param customer
     */
    void publicCustomer(Customer customer);

    /**
     * 编辑客户
     * @param customer
     */
    void editCustomer(Customer customer);

    /**
     * 转交客户给其他账号
     * @param customer 客户对象
     * @param toAccountId 转入账号ID
     */
    void tranCustomer(Customer customer, Integer toAccountId);

    /**
     * 导出客户资料文件为csv格式
     * @param outputStream
     * @param account
     */
    void exportCsvFileToOutputStream(OutputStream outputStream, Account account) throws IOException;

    /**
     * 导出客户资料文件为xls格式
     * @param outputStream
     * @param account
     */
    void exportXlsFileToOutputStream(OutputStream outputStream, Account account) throws IOException;

    /**
     * 查找属于当前Account对象的客户列表
     * @param account
     * @return
     */
    List<Customer> findAllCustomerByAccountId(Account account);

    /**
     * 查询各个星级客户的数量
     * @return
     */
    List<Map<String,Object>> findCustomerCountByLevel();
}
