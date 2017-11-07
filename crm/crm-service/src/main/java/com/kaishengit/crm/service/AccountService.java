package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.exception.AuthenticationException;

/**
 * Account业务层接口
 * @author fankay
 */
public interface AccountService {

    /**
     * 用户登录方法
     * @param mobile 手机号码
     * @param password 密码
     * @return 登录成功返回登录成功的对象,如果登录异常则抛出AuthenticationException异常
     */
    Account login(String mobile,String password) throws AuthenticationException;
}
