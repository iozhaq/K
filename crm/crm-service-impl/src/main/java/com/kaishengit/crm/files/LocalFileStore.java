package com.kaishengit.crm.files;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
public class LocalFileStore implements FileStore {

    @Value("${uploadfile.path}")
    private String saveFilePath;

    /**
     * 保存文件
     *
     * @param inputStream 文件输入流
     * @param fileName 文件的真实名称
     * @return 文件的存放路径或名称
     * @throws IOException
     */
    @Override
    public String saveFile(InputStream inputStream,String fileName) throws IOException {

        //重命名文件
        String newFileName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
        //本地磁盘
        FileOutputStream outputStream = new FileOutputStream(new File(saveFilePath, newFileName));
        IOUtils.copy(inputStream, outputStream);
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        return newFileName;
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
        InputStream inputStream = new FileInputStream(new File(saveFilePath,fileName));
        return IOUtils.toByteArray(inputStream);
    }
}
