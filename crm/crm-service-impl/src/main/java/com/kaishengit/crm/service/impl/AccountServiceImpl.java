package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.AccountDeptKey;
import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.example.AccountDeptExample;
import com.kaishengit.crm.example.AccountExample;
import com.kaishengit.crm.example.DeptExample;
import com.kaishengit.crm.exception.AuthenticationException;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.mapper.AccountDeptMapper;
import com.kaishengit.crm.mapper.AccountMapper;
import com.kaishengit.crm.mapper.DeptMapper;
import com.kaishengit.crm.service.AccountService;
import com.kaishengit.weixin.WeiXinUtil;
import com.kaishengit.weixin.exception.WeixinException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Account业务层的实现类
 * @author fankay
 */
@Service
public class AccountServiceImpl implements AccountService {

    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    /**
     * 部门表中公司的ID
     */
    private static final Integer COMPANY_ID = 1;


    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private AccountDeptMapper accountDeptMapper;
    @Autowired
    private WeiXinUtil weiXinUtil;


    /**
     * 用户登录方法
     *
     * @param mobile   手机号码
     * @param password 密码
     * @return 登录成功返回登录成功的对象, 如果登录异常则抛出AuthenticationException异常
     */
    @Override
    public Account login(String mobile, String password) throws AuthenticationException {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andMobileEqualTo(mobile);

        List<Account> accountList = accountMapper.selectByExample(accountExample);

        Account account = null;
        if(accountList != null && !accountList.isEmpty()) {
            account = accountList.get(0);
        }
        //判断account是否为null，并和传入的密码进行匹配
        if(account != null && account.getPassword().equals(password)) {
            logger.info("{} 在 {} 登录成功",account.getUserName(),new Date());
            return account;
        } else {
            throw new AuthenticationException("账号或密码错误");
        }
    }

    /**
     * 添加新部门
     *
     * @param deptName 部门名称
     * @throws ServiceException 例如添加部门名称已存在
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveNewDept(String deptName) throws ServiceException {
        //判断deptName是否存在
        DeptExample example = new DeptExample();
        example.createCriteria().andDeptNameEqualTo(deptName);
        List<Dept> deptList = deptMapper.selectByExample(example);
        Dept dept = null;
        if(deptList != null && !deptList.isEmpty()) {
            dept = deptList.get(0);
        }

        if(dept != null) {
            throw new ServiceException("部门名称已存在");
        }
        //添加新部门，使用公司ID作为父ID
        dept = new Dept();
        dept.setDeptName(deptName);
        dept.setpId(COMPANY_ID);

        deptMapper.insertSelective(dept);

        //发送到微信
        weiXinUtil.createDept(dept.getId(),COMPANY_ID,deptName);

        logger.info("添加新部门 {}",deptName);
    }

    /**
     * 查询所有的部门
     *
     * @return
     */
    @Override
    public List<Dept> findAllDept() {
        return deptMapper.selectByExample(new DeptExample());
    }

    /**
     * 根据查询参数获取Account的分页对象
     *
     * @param queryParam
     * @return
     */
    @Override
    public List<Account> pageForAccount(Map<String, Object> queryParam) {
        Integer start = (Integer) queryParam.get("start");
        Integer length = (Integer) queryParam.get("length");
        Integer deptId = (Integer) queryParam.get("deptId");
        String accountName = (String) queryParam.get("accountName");

        if(deptId == null || COMPANY_ID.equals(deptId)) {
            deptId = null;
        }

        List<Account> accountList = accountMapper.findByDeptId(accountName,deptId,start,length);

        return accountList;
    }

    /**
     * 根据部门ID获取账号的数量
     * @param deptId
     * @return
     */
    @Override
    public Long accountCountByDeptId(Integer deptId) {
        if(deptId == null || COMPANY_ID.equals(deptId)) {
            deptId = null;
        }
        return accountMapper.countByDeptId(deptId);
    }

    /**
     * 添加新的账号
     *
     * @param userName 账号名称
     * @param mobile   手机号码
     * @param password 密码（明文）
     * @param deptIds   所属部门ID，可以多个
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveNewEmployee(String userName, String mobile, String password, Integer[] deptIds) throws ServiceException {
        //1.验证手机号是否被使用
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andMobileEqualTo(mobile);

        List<Account> accountList = accountMapper.selectByExample(accountExample);
        if(accountList != null && !accountList.isEmpty()) {
            throw new ServiceException("该手机号已被使用");
        }
        //2.保存账号
        Account account = new Account();
        account.setUserName(userName);
        account.setPassword(password);
        account.setCreateTime(new Date());
        account.setUpdateTime(new Date());
        account.setMobile(mobile);

        accountMapper.insertSelective(account);
        //3.添加账号和部门的关系
        for(Integer deptId : deptIds) {
            AccountDeptKey accountDeptKey = new AccountDeptKey();
            accountDeptKey.setAccountId(account.getId());
            accountDeptKey.setDeptId(deptId);
            accountDeptMapper.insert(accountDeptKey);
        }

        //4.添加账号到微信
        weiXinUtil.createAccount(account.getId(),userName,mobile, Arrays.asList(deptIds));

        logger.info("添加新账号 {}",userName);
    }

    /**
     * 根据ID删除账号
     *
     * @param id
     * @throws ServiceException
     */
    @Override
    @Transactional
    public void deleteEmployeeById(Integer id) throws ServiceException {
        //0.TODO 判断其他的关联关系
        //1.删除关联关系
        AccountDeptExample accountDeptExample = new AccountDeptExample();
        accountDeptExample.createCriteria().andAccountIdEqualTo(id);

        accountDeptMapper.deleteByExample(accountDeptExample);
        //2.删除账号
        accountMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取所有的账号
     *
     * @return
     */
    @Override
    public List<Account> findAllAccount() {
        return accountMapper.selectByExample(new AccountExample());
    }

    /**
     * 根据手机号查询Account
     *
     * @param mobile
     * @return
     */
    @Override
    public Account findByMobile(String mobile) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andMobileEqualTo(mobile);
        List<Account> accountList = accountMapper.selectByExample(accountExample);
        if(accountList != null && !accountList.isEmpty()) {
            return accountList.get(0);
        }
        return null;
    }

    /**
     * 根据账号ID获取部门列表
     *
     * @param accountId
     * @return
     */
    @Override
    public List<Dept> findDeptByAccountId(Integer accountId) {
        return deptMapper.findDeptByAccountId(accountId);
    }
}
