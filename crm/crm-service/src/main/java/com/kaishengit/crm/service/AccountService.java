package com.kaishengit.crm.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.exception.AuthenticationException;
import com.kaishengit.crm.exception.ServiceException;

import java.util.List;
import java.util.Map;

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

    /**
     * 添加新部门
     * @param deptName 部门名称
     * @throws ServiceException 例如添加部门名称已存在
     */
    void saveNewDept(String deptName) throws ServiceException;

    /**
     * 查询所有的部门
     * @return
     */
    List<Dept> findAllDept();

    /**
     * 根据查询参数获取Account的分页对象
     * @param queryParam
     * @return
     */
    PageInfo<Account> pageForAccount(Map<String, Object> queryParam);
}
