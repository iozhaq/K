package com.kaishengit.weixin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-weixin.xml")
public class WeixinUtilTestcase {

    @Autowired
    private WeiXinUtil weiXinUtil;

    @Test
    public void getAccessToken() {
        String accessToken = weiXinUtil.getAccessToken(WeiXinUtil.ACCESSTOKEN_TYPE_CONTACTS);
        System.out.println(accessToken);
    }

    @Test
    public void createDept() {
        weiXinUtil.createDept(101,1,"开发部");
    }

    @Test
    public void deleteDept() {
        weiXinUtil.deleteDept(101);
    }

    @Test
    public void createAccount() {
        weiXinUtil.createAccount(1,"Jack","18788888888", Arrays.asList(101));
    }

    @Test
    public void deleteAccount() {
        weiXinUtil.deleteAccount(1);
    }

    @Test
    public void sendTextMessageToUser() {
        weiXinUtil.sendTextMessageToUser(Arrays.asList(100,109,108),"你有一个待办任务需要今天完成，请点击<a href=\"http://www.kaishengit.com\">链接</a>查看");
    }

}
