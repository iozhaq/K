package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.exception.AuthenticationException;
import com.kaishengit.crm.service.AccountService;
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
                        RedirectAttributes redirectAttributes,
                        HttpSession session) {
        try {
            Account account = accountService.login(mobile, password);
            session.setAttribute("curr_account",account);
            return "redirect:/home";
        } catch (AuthenticationException ex) {
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

}
