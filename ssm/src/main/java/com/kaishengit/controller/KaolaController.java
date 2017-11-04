package com.kaishengit.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Kaola;
import com.kaishengit.service.KaolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/product")
public class KaolaController {

    @Autowired
    private KaolaService kaolaService;

    @GetMapping
    public String list(@RequestParam(name = "p",required = false,defaultValue = "1") Integer pageNo,
                       Model model) {
        PageInfo<Kaola> pageInfo = kaolaService.findByPageNo(pageNo);
        model.addAttribute("pageInfo",pageInfo);
        return "product/list";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("typeList",kaolaService.findAllType());
        return "product/new";
    }

    @PostMapping("/new")
    public String newProduct(Kaola kaola, RedirectAttributes redirectAttributes) {
        kaolaService.save(kaola);
        redirectAttributes.addFlashAttribute("message","添加商品成功");
        return "redirect:/product";
    }

    @GetMapping("/{id:\\d+}")
    public String showProduct(@PathVariable Integer id,Model model) {
        Kaola kaola = kaolaService.findById(id);
        model.addAttribute("kaola",kaola);
        return "product/show";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String editProduct(@PathVariable Integer id,Model model) {
        model.addAttribute("typeList",kaolaService.findAllType());
        model.addAttribute("product",kaolaService.findById(id));
        return "product/edit";
    }

    @PostMapping("/{id:\\d+}/edit")
    public String editProduct(Kaola kaola,RedirectAttributes redirectAttributes) {
        kaolaService.editKaola(kaola);
        redirectAttributes.addFlashAttribute("message","修改成功");
        return "redirect:/product/"+kaola.getId();
    }

    @GetMapping("/{id:\\d+}/delete")
    public String delete(@PathVariable Integer id,RedirectAttributes redirectAttributes) {
        kaolaService.deleteKaolaById(id);
        redirectAttributes.addFlashAttribute("message","商品删除成功");
        return "redirect:/product";
    }


}
