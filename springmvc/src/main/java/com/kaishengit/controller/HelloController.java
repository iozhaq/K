package com.kaishengit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    //@RequestMapping(value = "/hello",method = {RequestMethod.GET,RequestMethod.POST})
    @GetMapping("/hello")
    public String sayHello() {
        System.out.println("Hello,SpringMVC");
        return "hello";
    }


}
