package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.example.CustomerExample;
import com.kaishengit.crm.mapper.AccountMapper;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.service.CustomerService;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客户管理业务层
 * @author fankay
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private AccountMapper accountMapper;

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

    /**
     * 删除指定的客户
     *
     * @param customer
     */
    @Override
    public void deleteCustomer(Customer customer) {
        //TODO 检查是否有关联交易记录
        //TODO 检查是否有关联代办事项
        //TODO 检查是否有关联资料文件
        customerMapper.deleteByPrimaryKey(customer.getId());
    }

    /**
     * 将指定客户放入公海
     *
     * @param customer
     */
    @Override
    public void publicCustomer(Customer customer) {
        Account account = accountMapper.selectByPrimaryKey(customer.getAccountId());

        customer.setAccountId(null);
        customer.setReminder(customer.getReminder() + "  " + account.getUserName() + "将该客户放入公海");
        customerMapper.updateByPrimaryKey(customer);
    }

    /**
     * 编辑客户
     *
     * @param customer
     */
    @Override
    public void editCustomer(Customer customer) {
        customer.setUpdateTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    /**
     * 转交客户给其他账号
     *
     * @param customer    客户对象
     * @param toAccountId 转入账号ID
     */
    @Override
    public void tranCustomer(Customer customer, Integer toAccountId) {
        Account account = accountMapper.selectByPrimaryKey(customer.getAccountId());
        customer.setAccountId(toAccountId);
        customer.setReminder(customer.getReminder() + " " + "从"+account.getUserName()+"转交过来");
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    /**
     * 导出客户资料文件为csv格式
     *
     * @param outputStream
     * @param account
     */
    @Override
    public void exportCsvFileToOutputStream(OutputStream outputStream, Account account) throws IOException {
        List<Customer> customerList = findAllCustomerByAccountId(account);

        StringBuilder sb = new StringBuilder();
        sb.append("姓名")
                .append(",")
                .append("联系电话")
                .append(",")
                .append("职位")
                .append(",")
                .append("地址")
                .append("\r\n");
        for(Customer customer : customerList) {
            sb.append(customer.getCustName())
                    .append(",")
                    .append(customer.getMobile())
                    .append(",")
                    .append(customer.getJobTitle())
                    .append(",")
                    .append(customer.getAddress())
                    .append("\r\n");
        }
        IOUtils.write(sb.toString(),outputStream,"GBK");

        outputStream.flush();
        outputStream.close();
    }

    /**
     * 导出客户资料文件为xls格式
     *
     * @param outputStream
     * @param account
     */
    @Override
    public void exportXlsFileToOutputStream(OutputStream outputStream, Account account) throws IOException {
        List<Customer> customerList = findAllCustomerByAccountId(account);
        //创建工作表
        Workbook workbook = new HSSFWorkbook();
        //创建sheet
        Sheet sheet = workbook.createSheet("我的客户");
        //创建行
        Row titleRow = sheet.createRow(0);
        //创建单元格
        Cell nameCell = titleRow.createCell(0);
        nameCell.setCellValue("姓名");
        titleRow.createCell(1).setCellValue("电话");
        titleRow.createCell(2).setCellValue("职位");
        titleRow.createCell(3).setCellValue("地址");

        for(int i = 0;i < customerList.size();i++) {
            Customer customer = customerList.get(i);

            Row dataRow = sheet.createRow(i+1);
            dataRow.createCell(0).setCellValue(customer.getCustName());
            dataRow.createCell(1).setCellValue(customer.getMobile());
            dataRow.createCell(2).setCellValue(customer.getJobTitle());
            dataRow.createCell(3).setCellValue(customer.getAddress());
        }


        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    public List<Customer> findAllCustomerByAccountId(Account account) {
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andAccountIdEqualTo(account.getId());
        List<Customer> customerList = customerMapper.selectByExample(customerExample);
        return customerList;
    }

    /**
     * 查询各个星级客户的数量
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> findCustomerCountByLevel() {
        return customerMapper.findCustomerCountByLevel();
    }
}
