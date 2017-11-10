package com.kaishengit.crm.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.service.AccountService;
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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 客户管理控制器
 * @author fankay
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountService accountService;


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
        Customer customer = checkCustomerRole(id,session);

        model.addAttribute("customer",customer);
        model.addAttribute("accountList",accountService.findAllAccount());
        return "customer/show";
    }

    /**
     * 根据主键删除客户
     * @param id
     * @param session
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/my/{id:\\d+}/delete")
    public String deleteCustomerById(@PathVariable Integer id,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        Customer customer = checkCustomerRole(id,session);
        customerService.deleteCustomer(customer);
        redirectAttributes.addFlashAttribute("message","删除客户成功");
        return "redirect:/customer/my";
    }

    /**
     * 将客户放入公海
     * @return
     */
    @GetMapping("/my/{id:\\d+}/public")
    public String publicCustomer(@PathVariable Integer id,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        Customer customer = checkCustomerRole(id,session);
        customerService.publicCustomer(customer);
        redirectAttributes.addFlashAttribute("message","将客户放入公海成功");
        return "redirect:/customer/my";

    }

    /**
     * 编辑客户
     */
    @GetMapping("/my/{id:\\d+}/edit")
    public String editCustomer(@PathVariable Integer id,
                               HttpSession session,
                               Model model) {
        Customer customer = checkCustomerRole(id,session);

        model.addAttribute("customer",customer);
        model.addAttribute("trades",customerService.findAllCustomerTrade());
        model.addAttribute("sources",customerService.findAllCustomerSource());
        return "customer/edit";
    }

    @PostMapping("/my/{id:\\d+}/edit")
    public String editCustomer(Customer customer,HttpSession session,RedirectAttributes redirectAttributes) {
        checkCustomerRole(customer.getId(),session);
        customerService.editCustomer(customer);
        redirectAttributes.addFlashAttribute("message","编辑成功");
        return "redirect:/customer/my/"+customer.getId();
    }

    /**
     * 转交客户
     * @return
     */
    @GetMapping("/my/{customerId:\\d+}/tran/{toAccountId:\\d+}")
    public String tranCustomer(@PathVariable Integer customerId,
                               @PathVariable Integer toAccountId,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        Customer customer = checkCustomerRole(customerId,session);
        customerService.tranCustomer(customer,toAccountId);

        redirectAttributes.addFlashAttribute("message","客户转交成功");
        return "redirect:/customer/my";
    }

    /**
     * 将数据导出为csv文件
     */
    @GetMapping("/my/export.csv")
    public void exportCsvData(HttpServletResponse response,
                              HttpSession session) throws IOException {
        Account account = getCurrentAccount(session);

        response.setContentType("text/csv;charset=GBK");
        String fileName = new String("我的客户.csv".getBytes("UTF-8"),"ISO8859-1");
        response.addHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");

        OutputStream outputStream = response.getOutputStream();
        customerService.exportCsvFileToOutputStream(outputStream,account);
    }

    /**
     * 将数据导出为xls文件
     */
    @GetMapping("/my/export.xls")
    public void exportXlsData(HttpServletResponse response,
                              HttpSession session) throws IOException {
        Account account = getCurrentAccount(session);

        response.setContentType("application/vnd.ms-excel");
        String fileName = new String("我的客户.xls".getBytes("UTF-8"),"ISO8859-1");
        response.addHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");

        OutputStream outputStream = response.getOutputStream();
        customerService.exportXlsFileToOutputStream(outputStream,account);
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


    /**
     * 验证客户是否属于当前登录的对象
     * @param id
     * @param session
     * @return
     */
    private Customer checkCustomerRole(Integer id,HttpSession session) {
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
        return customer;
    }
}
