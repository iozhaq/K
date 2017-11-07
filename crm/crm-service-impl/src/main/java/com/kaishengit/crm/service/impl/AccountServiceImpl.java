package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.example.AccountExample;
import com.kaishengit.crm.exception.AuthenticationException;
import com.kaishengit.crm.mapper.AccountMapper;
import com.kaishengit.crm.service.AccountService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Account业务层的实现类
 * @author fankay
 */
@Service
public class AccountServiceImpl implements AccountService {

    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountMapper accountMapper;


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
}
