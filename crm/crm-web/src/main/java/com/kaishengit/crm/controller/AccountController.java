package com.kaishengit.crm.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.service.AccountService;
import com.kaishengit.web.result.AjaxResult;
import com.kaishengit.web.result.DataTablesResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账号管理的控制器类
 * @author fankay
 */
@Controller
@RequestMapping("/employee")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @GetMapping
    public String list() {
        return "employee/list";
    }


    /**
     * DataTable插件请求
     */
    @GetMapping("/load.json")
    @ResponseBody
    public DataTablesResult<Account> loadEmployeeList(
            Integer draw, Integer start, Integer length, HttpServletRequest request) {
        String keyword = request.getParameter("search[value]");

        Map<String,Object> map = new HashMap<>();
        map.put("start",start);
        map.put("length",length);
        map.put("accountName",keyword);


        PageInfo<Account> accountPageInfo = accountService.pageForAccount(map);
        return new DataTablesResult<>(draw,new Long(accountPageInfo.getTotal()).intValue(),accountPageInfo.getList());
    }


    /**
     * 添加新部门
     * @param deptName
     * @return
     */
    @PostMapping("/dept/new")
    @ResponseBody
    public AjaxResult saveNewDept(String deptName) {
        try {
            accountService.saveNewDept(deptName);
            return AjaxResult.success();
        } catch (ServiceException ex) {
            ex.printStackTrace();
            return AjaxResult.error(ex.getMessage());
        }
    }

    /**
     * 获取部门名称JSON数据
     * @return
     */
    @GetMapping("/dept.json")
    @ResponseBody
    public List<Dept> findAllDept() {
        return accountService.findAllDept();
    }


}
