package com.kaishengit.controller;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Base64;

@Controller
public class HomeController {

    @GetMapping("/upload")
    public String upload(Model model) {
        //生成七牛上传文件的凭证
        String accessKey = "inOypU6O-rKyxyN22Z7Cq1uHMdc4YmhjE1GfJh7L";
        String secretKey = "l342cQNqBu2GSVUmhz_2by7yBPKJ7foeDH_0tN5r";
        String bucket = "java24";

        Auth auth = Auth.create(accessKey,secretKey);
        //String uploadToken = auth.uploadToken(bucket);

        //设置回调JSON格式
        //StringMap stringMap = new StringMap();
        //stringMap.put("returnBody","{\"fileKey\":\"$(key)\"}");

        StringMap stringMap = new StringMap();
        stringMap.put("returnUrl","http://localhost:8080/upload/callback");


        String uploadToken = auth.uploadToken(bucket,null,3600,stringMap);
        model.addAttribute("uploadToken",uploadToken);
        return "upload";
    }

    @GetMapping("/upload/callback")
    public String uploadCallBack(String upload_ret) {
        //eyJoYXNoIjoiRnVxc3B3MkRJVGhYb0k0cjVTek1BZWEzVU5KaCIsImtleSI6IkZ1cXNwdzJESVRoWG9JNHI1U3pNQWVhM1VOSmgifQ==
        //base64 解密
        String jsonString = new String(Base64.getDecoder().decode(upload_ret));
        System.out.println(jsonString);
        return "redirect:/upload";
    }
}

