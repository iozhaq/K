package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.example.DiskExample;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.files.FileStore;
import com.kaishengit.crm.mapper.DiskMapper;
import com.kaishengit.crm.service.DiskService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 公司网盘业务层
 * @author fankay
 */
@Service
public class DiskServiceImpl implements DiskService {

    @Autowired
    private DiskMapper diskMapper;

    @Autowired
    @Qualifier("qiniuFileStore")
    private FileStore fileStore;

    @Value("${uploadfile.path}")
    private String saveFilePath;

    /**
     * 创建新的文件夹
     *
     * @param disk
     */
    @Override
    public void saveNewFolder(Disk disk) {
        disk.setType(Disk.DISK_TYPE_FOLDER);
        disk.setUpdateTime(new Date());
        diskMapper.insertSelective(disk);
    }

    /**
     * 根据PID获取子文件夹及文件
     *
     * @param pid
     * @return
     */
    @Override
    public List<Disk> findDiskByPid(Integer pid) {
        DiskExample diskExample = new DiskExample();
        diskExample.createCriteria().andPIdEqualTo(pid);
        diskExample.setOrderByClause("type asc");
        return diskMapper.selectByExample(diskExample);
    }

    /**
     * 根据ID获取Disk对象
     *
     * @param id
     * @return
     */
    @Override
    public Disk findById(Integer id) {
        return diskMapper.selectByPrimaryKey(id);
    }

    /**
     * 上传文件
     *
     * @param inputStream 文件输入流
     * @param fileSize    文件大小 单位字节
     * @param fileName    文件名称
     * @param pId         所属文件夹的ID
     * @param accountId   上传文件的用户ID
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveNewFile(InputStream inputStream, long fileSize, String fileName, Integer pId, Integer accountId) {
        Disk disk = new Disk();
        disk.setType(Disk.DISK_TYPE_FILE);
        disk.setDownloadCount(0);
        disk.setAccountId(accountId);
        disk.setpId(pId);
        disk.setUpdateTime(new Date());
        disk.setName(fileName);
        //字节转换为可读大小
        disk.setFileSize(FileUtils.byteCountToDisplaySize(fileSize));


        String newFileName = null;
        try {
            newFileName = fileStore.saveFile(inputStream,fileName);
        } catch (IOException e) {
            throw new ServiceException(e,"保存文件异常");
        }
        disk.setSaveName(newFileName);
        diskMapper.insertSelective(disk);
    }

    /**
     * 根据ID获取文件的输入流
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public InputStream downloadFile(Integer id) throws IOException,ServiceException {
        Disk disk = diskMapper.selectByPrimaryKey(id);
        if(disk == null || disk.getType().equals(Disk.DISK_TYPE_FOLDER)) {
            throw new ServiceException(id+"对应的文件不存在或已被删除");
        }

        //更新下载数量
        disk.setDownloadCount(disk.getDownloadCount() +1);
        diskMapper.updateByPrimaryKeySelective(disk);

        byte[] bytes = fileStore.getFile(disk.getSaveName());
        return new ByteArrayInputStream(bytes);
    }
}
