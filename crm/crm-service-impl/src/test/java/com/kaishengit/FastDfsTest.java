package com.kaishengit;

import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FastDfsTest {

    @Test
    public void uploadFile() throws IOException, MyException {
        Properties properties = new Properties();
        properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS,"192.168.135.30:22122");
        //初始化配置
        ClientGlobal.initByProperties(properties);

        TrackerClient client = new TrackerClient();
        TrackerServer trackerServer = client.getConnection();
        //存储服务器的客户端
        StorageClient storageClient = new StorageClient(trackerServer,null);

        InputStream inputStream = new FileInputStream("D:/data.txt");
        byte[] bytes = IOUtils.toByteArray(inputStream);

        String[] results = storageClient.upload_file(bytes,"txt",null);
        for(String str : results) {
            System.out.println(str);
        }

        inputStream.close();
    }

    @Test
    public void downloadFile() throws IOException, MyException {
        Properties properties = new Properties();
        properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS,"192.168.135.30:22122");
        //初始化配置
        ClientGlobal.initByProperties(properties);

        TrackerClient client = new TrackerClient();
        TrackerServer trackerServer = client.getConnection();
        //存储服务器的客户端
        StorageClient storageClient = new StorageClient(trackerServer,null);

        byte[] bytes = storageClient.download_file("group1","M00/00/00/wKiHHloNQW6AashwAAFAPq7VyMI285.jpg");

        FileOutputStream outputStream = new FileOutputStream("D:/new.jpg");
        outputStream.write(bytes,0,bytes.length);
        outputStream.flush();
        outputStream.close();
    }
}
