package com.kaishengit.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import java.util.Arrays;

public class ShiroTestCase {

    @Test
    public void helloworld() {
        //1. 创建SecurityManager工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //2. 使用工厂创建SecurityManager对象
        SecurityManager securityManager = factory.getInstance();
        //3. 使用SecurityUtils设置SecurityManager
        SecurityUtils.setSecurityManager(securityManager);
        //4. 根据SecurityUtils创建Subject
        Subject subject = SecurityUtils.getSubject();
        //5. 登录
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("alex","1234567");
        try {
            subject.login(usernamePasswordToken);
            System.out.println("登录成功");
        } catch (UnknownAccountException ex) {
            System.out.println("该账号不存在");
        } catch (LockedAccountException ex) {
            System.out.println("该账号已被禁用");
        } catch (IncorrectCredentialsException ex) {
            System.out.println("账号或密码错误");
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
        }
        //6. 安全退出
        subject.logout();
    }

    @Test
    public void helloworldWithCustomerRealms() {
        //1. 创建SecurityManager工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        //2. 使用工厂创建SecurityManager对象
        SecurityManager securityManager = factory.getInstance();
        //3. 使用SecurityUtils设置SecurityManager
        SecurityUtils.setSecurityManager(securityManager);
        //4. 根据SecurityUtils创建Subject
        Subject subject = SecurityUtils.getSubject();
        //5. 登录
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("Alex","123123");
        try {
            subject.login(usernamePasswordToken);
            System.out.println("登录成功");
        } catch (UnknownAccountException ex) {
            System.out.println("该账号不存在");
        } catch (LockedAccountException ex) {
            System.out.println("该账号已被禁用");
        } catch (IncorrectCredentialsException ex) {
            System.out.println("账号或密码错误");
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
        }
        //6. 安全退出
        subject.logout();
    }

    @Test
    public void checkSubjectRole() {
        //1. 创建SecurityManager工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-roles.ini");
        //2. 使用工厂创建SecurityManager对象
        SecurityManager securityManager = factory.getInstance();
        //3. 使用SecurityUtils设置SecurityManager
        SecurityUtils.setSecurityManager(securityManager);
        //4. 根据SecurityUtils创建Subject
        Subject subject = SecurityUtils.getSubject();
        //5. 登录
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("tom","123123");
        try {
            subject.login(usernamePasswordToken);
            System.out.println("登录成功");

            //判断subject是否是admin
            System.out.println("is Admin?" + subject.hasRole("admin"));
            System.out.println("is HR?" + subject.hasRole("hr"));
            System.out.println("is Admin And CEO ? " + subject.hasAllRoles(Arrays.asList("admin","CEO")));
            boolean[] resultArray = subject.hasRoles(Arrays.asList("admin","CEO","CIO","HR"));
            for(boolean result : resultArray) {
                System.out.println(result);
            }
            //判断是否具有角色，如没有则抛异常
            //subject.checkRole("CIO");

            System.out.println("------------------------------");

            System.out.println("task:create ? " + subject.isPermitted("task:create"));
            System.out.println("user:create ? " + subject.isPermitted("user:create"));
            subject.checkPermission("task:create");


        } catch (UnknownAccountException ex) {
            System.out.println("该账号不存在");
        } catch (LockedAccountException ex) {
            System.out.println("该账号已被禁用");
        } catch (IncorrectCredentialsException ex) {
            System.out.println("账号或密码错误");
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
        }
        //6. 安全退出
        subject.logout();
    }

}
