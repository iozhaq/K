package com.kaishengit.crm.files;

import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class FastDfsFileStore implements FileStore {

    @Value("${fastdfs.tracker.server}")
    private String trackerServer;

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
        //获取文件的扩张名
        String extName = "";
        if(fileName.indexOf(".") != -1) {
            extName = fileName.substring(fileName.lastIndexOf(".") + 1);
        }

        StorageClient storageClient = getStorageClient();
        try {
            String[] result = storageClient.upload_file(IOUtils.toByteArray(inputStream), extName, null);

            StringBuilder stringBuilder = new StringBuilder();
            //group1#xxxxxxx.jpg
            stringBuilder.append(result[0])
                    .append("#")
                    .append(result[1]);
            return stringBuilder.toString();
        } catch (MyException ex) {
            throw new RuntimeException("存储文件到FastDFS异常",ex);
        }
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
        String[] array = fileName.split("#");
        String groupName = array[0];
        String filePath = array[1];

        StorageClient storageClient = getStorageClient();
        try {
            return storageClient.download_file(groupName, filePath);
        }catch (MyException ex) {
            throw new RuntimeException("从FastDFS获取文件异常",ex);
        }
    }

    /**
     * 获取FastDFS的StorageClient对象
     * @return
     */
    private StorageClient getStorageClient() {
        try {
            Properties properties = new Properties();
            properties.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS, trackerServer);
            //初始化配置
            ClientGlobal.initByProperties(properties);

            TrackerClient client = new TrackerClient();
            TrackerServer trackerServer = client.getConnection();
            //存储服务器的客户端
            StorageClient storageClient = new StorageClient(trackerServer, null);
            return storageClient;
        } catch (MyException | IOException ex) {
            throw new RuntimeException("获取StorageClient异常");
        }
    }
}
