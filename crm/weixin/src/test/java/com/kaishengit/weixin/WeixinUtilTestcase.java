package com.kaishengit.weixin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-weixin.xml")
public class WeixinUtilTestcase {

    @Autowired
    private WeiXinUtil weiXinUtil;

    @Test
    public void getAccessToken() {
        String accessToken = weiXinUtil.getAccessToken();
        accessToken = weiXinUtil.getAccessToken();
        accessToken = weiXinUtil.getAccessToken();
        System.out.println(accessToken);
    }
}
