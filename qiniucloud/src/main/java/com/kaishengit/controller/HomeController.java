package com.kaishengit.controller;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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


    @GetMapping("/upload/local")
    public String uploadLocal() {
        return "upload_local";
    }

    @PostMapping("/upload/local")
    public String uploadLocal(MultipartFile file) throws IOException {
        Configuration configuration = new Configuration(Zone.zone1());
        UploadManager uploadManager = new UploadManager(configuration);

        String accessKey = "inOypU6O-rKyxyN22Z7Cq1uHMdc4YmhjE1GfJh7L";
        String secretKey = "l342cQNqBu2GSVUmhz_2by7yBPKJ7foeDH_0tN5r";
        String bucket = "java24";

        Auth auth = Auth.create(accessKey,secretKey);
        String uploadToken = auth.uploadToken(bucket);

        byte[] bytes = file.getBytes();
        Response response = uploadManager.put(bytes,null,uploadToken);
        //System.out.println(response.bodyString());
        DefaultPutRet defaultPutRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
        System.out.println("key -> " + defaultPutRet.key);

        return "redirect:/upload/local";
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {

        String domainOfBucket = "http://ozoybvszl.bkt.clouddn.com";
        String encodedFileName = URLEncoder.encode(name, "utf-8");
        String finalUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        //如果是私有空间，需要通过Auth对象获取临时的下载路径
        /*String accessKey = "your access key";
        String secretKey = "your secret key";
        Auth auth = Auth.create(accessKey, secretKey);
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);*/
        System.out.println(finalUrl);


        HttpURLConnection urlConnection = (HttpURLConnection) new URL(finalUrl).openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        OutputStream outputStream = response.getOutputStream();

        IOUtils.copy(inputStream,outputStream);
        outputStream.flush();
        outputStream.close();
        inputStream.close();


    }
}

