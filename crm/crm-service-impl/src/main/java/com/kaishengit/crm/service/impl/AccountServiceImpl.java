package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.example.AccountExample;
import com.kaishengit.crm.example.DeptExample;
import com.kaishengit.crm.exception.AuthenticationException;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.mapper.AccountMapper;
import com.kaishengit.crm.mapper.DeptMapper;
import com.kaishengit.crm.service.AccountService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public PageInfo<Account> pageForAccount(Map<String, Object> queryParam) {
        Integer start = (Integer) queryParam.get("start");
        Integer length = (Integer) queryParam.get("length");
        PageHelper.offsetPage(start,length);
        //搜索的用户名
        String accountName = (String) queryParam.get("accountName");

        AccountExample accountExample = new AccountExample();
        AccountExample.Criteria criteria = accountExample.createCriteria();

        if(StringUtils.isNotEmpty(accountName)) {
            criteria.andUserNameLike("%"+accountName+"%");
        }

        List<Account> accountList = accountMapper.selectByExample(accountExample);

        return new PageInfo<>(accountList);
    }
}
