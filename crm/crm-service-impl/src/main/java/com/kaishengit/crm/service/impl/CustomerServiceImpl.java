package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.example.CustomerExample;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 客户管理业务层
 * @author fankay
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerMapper customerMapper;

    //SpringEL
    @Value("#{'${customer.trade}'.split(',')}")
    private List<String> customerTrade;
    @Value("#{'${customer.source}'.split(',')}")
    private List<String> customerSource;

    /**
     * 我的客户 分页方法
     *
     * @param account 当前登录的account对象
     * @param pageNo  页号
     * @return
     */
    @Override
    public PageInfo<Customer> pageForMyCustomer(Account account, Integer pageNo) {
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andAccountIdEqualTo(account.getId());

        PageHelper.startPage(pageNo,15);
        List<Customer> customerList = customerMapper.selectByExample(customerExample);

        return new PageInfo<>(customerList);
    }

    /**
     * 获取所有客户行业名称
     *
     * @return
     */
    @Override
    public List<String> findAllCustomerTrade() {
        return customerTrade;
    }

    /**
     * 获取所有客户来源名称
     *
     * @return
     */
    @Override
    public List<String> findAllCustomerSource() {
        return customerSource;
    }

    /**
     * 添加新客户
     * @param customer
     */
    @Override
    public void saveNewCustomer(Customer customer) {
        customer.setCreateTime(new Date());
        customer.setUpdateTime(new Date());
        customerMapper.insertSelective(customer);
        logger.info("添加新客户 {}" , customer.getCustName());
    }

    /**
     * 根据主键查询客户
     *
     * @param id
     * @return
     */
    @Override
    public Customer findCustomerById(Integer id) {
        return customerMapper.selectByPrimaryKey(id);
    }
}
