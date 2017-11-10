package com.kaishengit.crm.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 客户管理控制器
 * @author fankay
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;


    /**
     * 访问我的客户
     * @return
     */
    @GetMapping("/my")
    public String myCustomer(@RequestParam(required = false,defaultValue = "1") Integer pageNo,
                             Model model,
                             HttpSession httpSession) {
        Account account = getCurrentAccount(httpSession);
        PageInfo<Customer> pageInfo = customerService.pageForMyCustomer(account,pageNo);

        model.addAttribute("page",pageInfo);
        return "customer/my";
    }

    /**
     * 新增客户
     * @return
     */
    @GetMapping("/my/new")
    public String newCustomer(Model model) {

        model.addAttribute("trades",customerService.findAllCustomerTrade());
        model.addAttribute("sources",customerService.findAllCustomerSource());
        return "customer/new";
    }

    @PostMapping("/my/new")
    public String newCustomer(Customer customer, RedirectAttributes redirectAttributes) {
        customerService.saveNewCustomer(customer);
        redirectAttributes.addFlashAttribute("message","添加客户成功");
        return "redirect:/customer/my";
    }


    /**
     * 显示客户详细信息
     * @return
     */
    @GetMapping("/my/{id:\\d+}")
    public String showCustomer(@PathVariable Integer id,HttpSession session,Model model) {
        //根据ID查找客户
        Customer customer = customerService.findCustomerById(id);
        if(customer == null) {
            //404
            throw new NotFoundException("找不到"+id+"对应的客户");
        }

        Account account = getCurrentAccount(session);
        if(!customer.getAccountId().equals(account.getId())) {
            //403 Forbidden
            throw new ForbiddenException("没有查看客户"+ id + "的权限");
        }

        model.addAttribute("customer",customer);
        return "customer/show";
    }


    /**
     * 公海客户列表
     * @return
     *
     */
    @GetMapping("/public")
    public String publicCustomer() {
        return "customer/public";
    }



}
