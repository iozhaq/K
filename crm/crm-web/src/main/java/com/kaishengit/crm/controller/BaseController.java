package com.kaishengit.crm.controller;

import com.kaishengit.crm.auth.ShiroUtil;
import com.kaishengit.crm.entity.Account;

import javax.servlet.http.HttpSession;

/**
 * 所有控制器类的父类，提供控制器中使用的公共方法
 * @author fankay
 */
public abstract class BaseController {

    /**
     * 获取当前登录系统的账户对象
     * @param session
     * @return
     */
    public Account getCurrentAccount(HttpSession session) {
        return ShiroUtil.getCurrentAccount();
    }
}
