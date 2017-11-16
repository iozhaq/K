package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.exception.ServiceException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 公司网盘业务层
 * @author fankay
 */
public interface DiskService {
    /**
     * 创建新的文件夹
     * @param disk
     */
    void saveNewFolder(Disk disk);

    /**
     * 根据PID获取子文件夹及文件
     * @param pid
     * @return
     */
    List<Disk> findDiskByPid(Integer pid);

    /**
     * 根据ID获取Disk对象
     * @param id
     * @return
     */
    Disk findById(Integer id);

    /**
     * 上传文件
     * @param inputStream 文件输入流
     * @param fileSize 文件大小 单位字节
     * @param fileName 文件名称
     * @param pId 所属文件夹的ID
     * @param accountId 上传文件的用户ID
     */
    void saveNewFile(InputStream inputStream, long fileSize, String fileName, Integer pId, Integer accountId);

    /**
     * 根据ID获取文件的输入流
     * @param id
     * @return
     */
    InputStream downloadFile(Integer id) throws ServiceException,IOException;
}
