package com.kaishengit;

import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
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

        InputStream inputStream = new FileInputStream("D:/11.jpg");
        byte[] bytes = IOUtils.toByteArray(inputStream);

        String[] results = storageClient.upload_file(bytes,"jpg",null);
        for(String str : results) {
            System.out.println(str);
        }

        inputStream.close();
    }


    @Test
    public void uploadFileWithMetaData() throws IOException, MyException {
        Properties properties = new Properties();
        properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS,"192.168.135.30:22122");
        //初始化配置
        ClientGlobal.initByProperties(properties);

        TrackerClient client = new TrackerClient();
        TrackerServer trackerServer = client.getConnection();
        //存储服务器的客户端
        StorageClient storageClient = new StorageClient(trackerServer,null);

        InputStream inputStream = new FileInputStream("D:/220.jpg");
        byte[] bytes = IOUtils.toByteArray(inputStream);

        //metadata
        NameValuePair[] nameValuePairs = new NameValuePair[3];
        nameValuePairs[0] = new NameValuePair("width","600");
        nameValuePairs[1] = new NameValuePair("device","iPhone 8 plus");
        nameValuePairs[2] = new NameValuePair("iso","3000");

        String[] results = storageClient.upload_file(bytes,"jpg",nameValuePairs);
        for(String str : results) {
            System.out.println(str);
        }

        //group1
        //M00/00/00/wKiHHloOfu-AYf8DAADY3bRaJg0184.jpg

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

    @Test
    public void downloadFileWithMeteData() throws IOException, MyException {
        Properties properties = new Properties();
        properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS,"192.168.135.30:22122");
        //初始化配置
        ClientGlobal.initByProperties(properties);

        TrackerClient client = new TrackerClient();
        TrackerServer trackerServer = client.getConnection();
        //存储服务器的客户端
        StorageClient storageClient = new StorageClient(trackerServer,null);

        byte[] bytes = storageClient.download_file("group1","M00/00/00/wKiHHloOfu-AYf8DAADY3bRaJg0184.jpg");

        //获取Metadata数组
        NameValuePair[] nameValuePairs = storageClient.get_metadata("group1","M00/00/00/wKiHHloOfu-AYf8DAADY3bRaJg0184.jpg");
        for(NameValuePair nameValuePair : nameValuePairs) {
            System.out.println(nameValuePair.getName() + " -> " + nameValuePair.getValue());
        }

        //FileInfo
        FileInfo fileInfo = storageClient.get_file_info("group1","M00/00/00/wKiHHloOfu-AYf8DAADY3bRaJg0184.jpg");
        System.out.println("fileSize:" + fileInfo.getFileSize());
        System.out.println("crc32 :" + fileInfo.getCrc32());
        System.out.println("createtime:" + fileInfo.getCreateTimestamp());
        System.out.println("ip:" + fileInfo.getSourceIpAddr());

        FileOutputStream outputStream = new FileOutputStream("D:/new.jpg");
        outputStream.write(bytes,0,bytes.length);
        outputStream.flush();
        outputStream.close();
    }
}
