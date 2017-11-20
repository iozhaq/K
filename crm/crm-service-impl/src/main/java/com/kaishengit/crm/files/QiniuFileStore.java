package com.kaishengit.crm.files;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 七牛云存储文件处理
 * @author fankay
 */
@Component
public class QiniuFileStore implements FileStore {

    @Value("${qiniu.ak}")
    private String qiniuAccessKey;
    @Value("${qiniu.sk}")
    private String qiuiuSecretKey;
    @Value("${qiniu.bucketName}")
    public String bucketName;
    @Value("${qiniu.domain}")
    public String domain;

    /**
     * 保存文件
     *
     * @param inputStream 文件输入流
     * @param fileName    文件的真实名称
     * @return 文件的存放路径或名称
     * @throws IOException
     */
    @Override
    public String saveFile(InputStream inputStream, String fileName) throws IOException {
        Configuration configuration = new Configuration(Zone.zone1());
        UploadManager uploadManager = new UploadManager(configuration);

        Auth auth = Auth.create(qiniuAccessKey,qiuiuSecretKey);
        String uploadToken = auth.uploadToken(bucketName);

        byte[] bytes = IOUtils.toByteArray(inputStream);
        Response response = uploadManager.put(bytes,null,uploadToken);
        DefaultPutRet defaultPutRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
        return defaultPutRet.key;
    }

    /**
     * 获取文件
     *
     * @param fileName 文件名称或路径
     * @return 文件的字节数组
     * @throws IOException
     */
    @Override
    public byte[] getFile(String fileName) throws IOException {
        String finalName = String.format("%s/%s",domain,fileName);
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(finalName).openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        return IOUtils.toByteArray(inputStream);
    }
}
