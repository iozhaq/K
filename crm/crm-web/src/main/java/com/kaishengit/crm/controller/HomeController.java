package com.kaishengit.crm.controller;

import com.kaishengit.crm.auth.ShiroUtil;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public String login() {
        Subject subject = ShiroUtil.getSubject();
        System.out.println("isAuthenticated ? " + subject.isAuthenticated());
        System.out.println("isRemembered? " + subject.isRemembered());

        if(subject.isAuthenticated()) {
            //认为用户是要切换账号
            subject.logout();
        }
        if(!subject.isAuthenticated() && subject.isRemembered()) {
            //被记住的用户直接去登录成功页面
            return "redirect:/home";
        }

        return "index";
    }

    /**
     * 表单登录方法
     * @param mobile
     * @param password
     * @return
     */
    @PostMapping("/")
    public String login(String mobile, String password, boolean rememberMe,
                        RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken =
                    new UsernamePasswordToken(mobile,new Md5Hash(password).toString(),rememberMe);
            subject.login(usernamePasswordToken);

            //获取当前登录的对象
            //Account account = (Account) subject.getPrincipal();
            //将登录成功的对象放入Session
            //Session session = subject.getSession();
            //session.setAttribute("curr_account",account);

            //跳转到登录前访问的URL
            String url = "/home";
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            if(savedRequest != null) {
                //获取登录前访问的URL
                url = savedRequest.getRequestUrl();
            }
            return "redirect:"+url;
        } catch (AuthenticationException ex) {
            redirectAttributes.addFlashAttribute("message","账号或密码错误");
            return "redirect:/";
        }
    }

    /**
     * 安全退出
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session,RedirectAttributes redirectAttributes) {
       SecurityUtils.getSubject().logout();
       redirectAttributes.addFlashAttribute("message","你已安全退出系统");
       return "redirect:/";
    }

    /**
     * 去登录后的页面
     * @return
     */
    @GetMapping("/home")
    public String home() {
        return "home";
    }

}
