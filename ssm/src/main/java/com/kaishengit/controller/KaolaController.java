package com.kaishengit.controller;

import com.kaishengit.entity.Kaola;
import com.kaishengit.service.KaolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class KaolaController {

    @Autowired
    private KaolaService kaolaService;

    @GetMapping("/product/{id:\\d+}")
    @ResponseBody
    public Kaola findById(@PathVariable Integer id) {
        Kaola kaola = kaolaService.findById(id);
        return kaola;
    }

    @GetMapping("/product")
    @ResponseBody
    public List<Kaola> page(@RequestParam(required = false,defaultValue = "1",name = "p") Integer pageNo) {
        return kaolaService.findByPageNo(pageNo);
    }

}
