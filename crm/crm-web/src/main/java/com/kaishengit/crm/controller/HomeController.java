package com.kaishengit.crm.controller;

import com.kaishengit.crm.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public String login() {
        return "index";
    }

    /**
     * 表单登录方法
     * @param mobile
     * @param password
     * @return
     */
    @PostMapping("/")
    public String login(String mobile, String password,
                        RedirectAttributes redirectAttributes) {
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken =
                    new UsernamePasswordToken(mobile,new Md5Hash(password).toString());
            subject.login(usernamePasswordToken);
            return "redirect:/home";
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
