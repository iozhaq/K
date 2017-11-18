package com.kaishengit.crm.files;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件存储的接口
 * @author fankay
 */
public interface FileStore {

    /**
     * 保存文件
     * @param inputStream 文件输入流
     * @param fileName 文件的真实名称
     * @return 文件的存放路径或名称
     * @throws IOException
     */
    String saveFile(InputStream inputStream,String fileName) throws IOException;

    /**
     * 获取文件
     * @param fileName 文件名称或路径
     * @return 文件的字节数组
     * @throws IOException
     */
    byte[] getFile(String fileName) throws IOException;
}
