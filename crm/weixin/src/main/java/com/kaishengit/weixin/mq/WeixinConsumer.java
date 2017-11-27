package com.kaishengit.weixin.mq;

import com.alibaba.fastjson.JSON;
import com.kaishengit.weixin.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class WeixinConsumer {
    @Autowired
    private WeiXinUtil weiXinUtil;

    @JmsListener(destination = "weixinMessage-Queue")
    public void sendMessageToUser(String json) {
        //String json = "{\"id\":\"fankay\",\"message\":\"Hello,Message from JMS\"}";
        Map<String,Object> map = JSON.parseObject(json, HashMap.class);
        weiXinUtil.sendTextMessageToUser(Arrays.asList(1,2,34),map.get("message").toString());
    }
}
