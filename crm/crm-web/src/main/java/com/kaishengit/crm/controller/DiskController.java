package com.kaishengit.crm.controller;

import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Disk;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.service.DiskService;
import com.kaishengit.web.result.AjaxResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 公司网盘控制器层
 * @author fankay
 */
@Controller
@RequestMapping("/disk")
public class DiskController {

    @Autowired
    private DiskService diskService;

    @GetMapping
    public String list(Model model,
                       @RequestParam(required = false,defaultValue = "0",name = "_") Integer pid) {
        List<Disk> diskList = diskService.findDiskByPid(pid);
        if(pid != 0) {
            Disk disk = diskService.findById(pid);
            model.addAttribute("disk",disk);
        }
        model.addAttribute("diskList",diskList);
        return "disk/home";
    }

    /**
     * 新建文件夹
     * @return
     */
    @PostMapping("/new/folder")
    @ResponseBody
    public AjaxResult saveNewFolder(Disk disk) {
        //保存文件夹
        diskService.saveNewFolder(disk);
        //获取当前最新的集合
        List<Disk> diskList = diskService.findDiskByPid(disk.getpId());
        return AjaxResult.successWithData(diskList);
    }

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult uploadFile(Integer pId, Integer accountId, MultipartFile file) throws IOException {
        if(file.isEmpty()) {
            return AjaxResult.error("文件不可缺少");
        }
        //获取文件输入流
        InputStream inputStream = file.getInputStream();
        //获取文件大小
        long fileSize = file.getSize();
        //获取文件真正的名称
        String fileName = file.getOriginalFilename();

        diskService.saveNewFile(inputStream,fileSize,fileName,pId,accountId);

        //获取当前最新的集合
        List<Disk> diskList = diskService.findDiskByPid(pId);
        return AjaxResult.successWithData(diskList);
    }

    /**
     * 文件下载
     */
    @GetMapping("/download")
    public void downloadFile(@RequestParam(name = "_") Integer id,
                             @RequestParam(required = false,defaultValue = "") String fileName,
                             HttpServletResponse response) {
        try {
            OutputStream outputStream = response.getOutputStream();
            InputStream inputStream = diskService.downloadFile(id);

            //判断是下载还是预览
            if(StringUtils.isNotEmpty(fileName)) {
                //下载
                //设置mimetype
                response.setContentType("application/octet-stream");
                //设置下载对话框
                fileName = new String(fileName.getBytes("UTF-8"),"ISO8859-1");
                response.addHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");
            }
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException | ServiceException ex) {
            ex.printStackTrace();
            throw new NotFoundException();
        }
    }
}
