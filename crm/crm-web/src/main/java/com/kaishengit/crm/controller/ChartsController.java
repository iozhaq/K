package com.kaishengit.crm.controller;

import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.web.result.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 统计报表控制器
 * @author fankay
 */
@Controller
@RequestMapping("/charts")
public class ChartsController extends BaseController {

    @Autowired
    private CustomerService customerService;

    /**
     * 静态数据演示
     * @return
     */
    @GetMapping("/demo")
    public String demo() {
        return "charts/static";
    }

    @GetMapping
    public String show() {
        return "charts/chart";
    }

    @GetMapping("/customer/level")
    @ResponseBody
    public AjaxResult customerLevelData() {
        List<Map<String,Object>> result = customerService.findCustomerCountByLevel();
        return AjaxResult.successWithData(result);
    }

}
