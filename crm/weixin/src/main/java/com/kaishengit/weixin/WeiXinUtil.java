package com.kaishengit.weixin;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaishengit.weixin.exception.WeixinException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class WeiXinUtil {


    private static final String GET_ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";


    @Value("${weixin.corpID}")
    private String corpID;
    @Value("${weixin.secret}")
    private String secret;

    /**
     * AccessToken的缓存
     */
    private LoadingCache<String,String> accessTokenCache = CacheBuilder.newBuilder()
            .expireAfterWrite(7200, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    String url = String.format(GET_ACCESS_TOKEN_URL,corpID,secret);
                    String resultJson = sendHttpGetRequest(url);
                    Map<String,Object> map = JSON.parseObject(resultJson, HashMap.class);
                    if(map.get("errcode").equals(0)) {
                        return (String) map.get("access_token");
                    }
                    throw new WeixinException(resultJson);
                }
            });


    /**
     * 获取AccessToken
     * @return
     */
    public String getAccessToken() {
        try {
            return accessTokenCache.get("");
        } catch (ExecutionException e) {
            throw new RuntimeException("获取AccessToken异常",e);
        }
    }

    /**
     * 发出Http的get请求
     * @Param url 请求的URL地址
     */
    private String sendHttpGetRequest(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException ex) {
            throw new RuntimeException("HTTP请求异常",ex);
        }
    }

    /**
     * 发出Http的Post请求
     */
    private void sendHttpPostRequest() {

    }

}
