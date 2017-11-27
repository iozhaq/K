package com.kaishengit.crm.auth;

import com.kaishengit.crm.entity.Account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Shiro的工具类
 * @author fankay
 */
public class ShiroUtil {

    /**
     * 获取当前登录的对象
     * @return
     */
    public static Account getCurrentAccount() {
        return (Account) getSubject().getPrincipal();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }
}
