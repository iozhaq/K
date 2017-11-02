package com.kaishengit.controller;

import com.kaishengit.entity.User;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class HelloController {

    //@RequestMapping(value = "/hello",method = {RequestMethod.GET,RequestMethod.POST})
    @GetMapping("/hello")
    public String sayHello() {
        System.out.println("Hello,SpringMVC");
        return "hello";
    }

    @GetMapping("/send")
    public ModelAndView send() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("send"); //设置视图的名字
        modelAndView.addObject("message","使用ModelAndView对象");

        return modelAndView;
    }
    /*public String send(Model model) {
        model.addAttribute("message","你好,SpringMVC");
        return "send";
    }*/

    /**
     * /blog?id=100&tag=200
     * /blog?id=100
     * @param id
     * @return
     */
    @GetMapping("/blog")
    public String blog(Integer id,
                       @RequestParam(name = "tag",required = false,defaultValue = "1") Integer tagId, Model model) {
        System.out.println("id: " + id + " tagId:" + tagId);

        model.addAttribute("id",id);
        model.addAttribute("tagId",tagId);
        return "hello";
    }

    @GetMapping("/movie/{id:\\d+}")
    public String movie(@PathVariable Integer id) {
        System.out.println("Movie Id :" + id);
        return "hello";
    }

    @GetMapping("/class/{className}/user/{userId:\\d+}")
    public String findUser(@PathVariable String className,
                           @PathVariable Integer userId) {
        System.out.println("ClassName : " + className + "  UserId:" + userId);
        return "hello";
    }

    @GetMapping("/save")
    public String save() {
        return "save";
    }

    @PostMapping("/save")
    public String save(User user, String email,
                       MultipartFile photo,
                       RedirectAttributes redirectAttributes) {
        System.out.println("userName -> " + user.getUserName() + " password-> " + user.getPassword() + " email ->" + email);

        //判断是否上传了图片
        if(!photo.isEmpty()) {
            System.out.println("文件名称 -> " + photo.getOriginalFilename());
            System.out.println("文件大小- > " + photo.getSize());
            System.out.println("MIMEType -> " + photo.getContentType());

            try {
                IOUtils.copy(photo.getInputStream(),new FileOutputStream("D:/temp/"+photo.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        redirectAttributes.addFlashAttribute("message","资料审核中");
        return "redirect:/save";
    }


}
