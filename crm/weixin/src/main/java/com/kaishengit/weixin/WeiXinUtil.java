package com.kaishengit.weixin;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaishengit.weixin.exception.WeixinException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class WeiXinUtil {

    public static final String ACCESSTOKEN_TYPE_NORMAL = "normal";
    public static final String ACCESSTOKEN_TYPE_CONTACTS = "contacts";

    /**
     * 获取AccessToken的URL
     */
    private static final String GET_ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
    /**
     * 创建部门的URL
     */
    private static final String POST_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=%s";
    /**
     * 删除部门的URL
     */
    private static final String GET_DELETE_DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=%s&id=%s";

    /**
     * 创建员工的URL
     */
    private static final String POST_CREATE_ACCOUNT_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=%s";

    /**
     * 删除员工的URL
     */
    private static final String GET_DELETE_ACCOUNT_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=%s&userid=%s";

    /**
     * 发送消息的URL
     */
    private static final String POST_SEND_MESSAGE = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%s";

    @Value("${weixin.corpID}")
    private String corpID;
    @Value("${weixin.secret}")
    private String secret;
    @Value("${weixin.concat.secret}")
    private String contactsSecret;
    @Value("${weixin.app.agentid}")
    private Integer agentId;

    /**
     * AccessToken的缓存
     */
    private LoadingCache<String, String> accessTokenCache = CacheBuilder.newBuilder()
            .expireAfterWrite(7200, TimeUnit.SECONDS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String type) throws Exception {
                    String url;
                    //判断获取的是普通的AccessToken还是通讯录的AccessToken
                    if (ACCESSTOKEN_TYPE_CONTACTS.equals(type)) {
                        url = String.format(GET_ACCESS_TOKEN_URL, corpID, contactsSecret);
                    } else {
                        url = String.format(GET_ACCESS_TOKEN_URL, corpID, secret);
                    }
                    String resultJson = sendHttpGetRequest(url);
                    Map<String, Object> map = JSON.parseObject(resultJson, HashMap.class);
                    if (map.get("errcode").equals(0)) {
                        return (String) map.get("access_token");
                    }
                    throw new WeixinException(resultJson);
                }
            });


    /**
     * 获取AccessToken
     *
     * @param type 获取AccessToken的类型  normal  Contacts
     * @return
     */
    public String getAccessToken(String type) {
        try {
            return accessTokenCache.get(type);
        } catch (ExecutionException e) {
            throw new RuntimeException("获取AccessToken异常", e);
        }
    }

    /**
     * 创建部门
     *
     * @param id
     * @param parentId
     * @param name
     */
    public void createDept(Integer id, Integer parentId, String name) {
        String url = String.format(POST_DEPT_URL, getAccessToken(ACCESSTOKEN_TYPE_CONTACTS));

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("name", name);
        data.put("parentid", parentId);
        data.put("id", id);

        String resultJson = sendHttpPostRequest(url, JSON.toJSONString(data));
        Map<String, Object> resultMap = JSON.parseObject(resultJson, HashMap.class);
        if (!resultMap.get("errcode").equals(0)) {
            throw new WeixinException("创建部门失败:" + resultJson);
        }
    }

    /**
     * 删除部门
     *
     * @param id 部门主键
     */
    public void deleteDept(Integer id) {
        String url = String.format(GET_DELETE_DEPT_URL, getAccessToken(ACCESSTOKEN_TYPE_CONTACTS), id);
        String resultJson = sendHttpGetRequest(url);
        Map<String, Object> resultMap = JSON.parseObject(resultJson, HashMap.class);
        if (!resultMap.get("errcode").equals(0)) {
            throw new WeixinException("删除部门异常: " + resultJson);
        }
    }

    /**
     * 创建账号
     *
     * @param accountId        账号ID（唯一）
     * @param name             账号姓名
     * @param mobile           手机号码（唯一）
     * @param departmentIdList 所属部门的ID列表
     */
    public void createAccount(Integer accountId, String name, String mobile, List<Integer> departmentIdList) {
        String url = String.format(POST_CREATE_ACCOUNT_URL, getAccessToken(ACCESSTOKEN_TYPE_CONTACTS));

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("userid", accountId);
        data.put("name", name);
        data.put("mobile", mobile);
        data.put("department", departmentIdList);

        String resultJson = sendHttpPostRequest(url, JSON.toJSONString(data));
        Map<String, Object> resultMap = JSON.parseObject(resultJson, HashMap.class);
        if (!resultMap.get("errcode").equals(0)) {
            throw new WeixinException("创建账号失败: " + resultJson);
        }
    }

    /**
     * 删除账号
     *
     * @param id 账号ID
     */
    public void deleteAccount(Integer id) {
        String url = String.format(GET_DELETE_ACCOUNT_URL, getAccessToken(ACCESSTOKEN_TYPE_CONTACTS), id);
        String resultJson = sendHttpGetRequest(url);
        Map<String, Object> resultMap = JSON.parseObject(resultJson, HashMap.class);
        if (!resultMap.get("errcode").equals(0)) {
            throw new WeixinException("删除账号失败: " + resultJson);
        }
    }

    /**
     * 发送文本消息给用户
     *
     * @param userIdList 接收消息的用户ID
     * @param message    消息内容（支持转义字符和html）
     */
    public void sendTextMessageToUser(List<Integer> userIdList, String message) {
        String url = String.format(POST_SEND_MESSAGE, getAccessToken(ACCESSTOKEN_TYPE_NORMAL));

        StringBuilder stringBuilder = new StringBuilder();
        for(Integer userId : userIdList) {
            stringBuilder.append(userId).append("|");
        }
        String idString = stringBuilder.toString();
        idString = idString.substring(0,idString.lastIndexOf("|"));

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("touser", "fankay");
        data.put("msgtype", "text");
        data.put("agentid", agentId);
        Map<String, String> messageMap = new HashMap<String, String>();
        messageMap.put("content", message);
        data.put("text", messageMap);

        String resultJson = sendHttpPostRequest(url, JSON.toJSONString(data));

        Map<String, Object> resultMap = JSON.parseObject(resultJson, HashMap.class);
        if (!resultMap.get("errcode").equals(0)) {
            throw new WeixinException("发送文本消息失败: " + resultJson);
        }
    }


    /**
     * 发出Http的get请求
     *
     * @Param url 请求的URL地址
     */
    private String sendHttpGetRequest(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException ex) {
            throw new RuntimeException("HTTP请求异常", ex);
        }
    }

    /**
     * 发出Http的Post请求
     *
     * @param url  目标的URL
     * @param json 请求体
     */
    private String sendHttpPostRequest(String url, String json) {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        //通过JSON格式构建Post的请求体
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException ex) {
            throw new RuntimeException("HTTP请求异常", ex);
        }
    }

}
