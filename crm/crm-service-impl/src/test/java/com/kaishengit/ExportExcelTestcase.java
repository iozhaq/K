package com.kaishengit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExportExcelTestcase {

    @Test
    public void export() throws IOException {
        //创建工作表
        Workbook workbook = new HSSFWorkbook();
        //创建sheet
        Sheet sheet = workbook.createSheet("我的客户");
        //创建行
        Row titleRow = sheet.createRow(0);
        //创建单元格
        Cell nameCell = titleRow.createCell(0);
        nameCell.setCellValue("姓名");
        titleRow.createCell(1).setCellValue("电话");
        titleRow.createCell(2).setCellValue("职位");
        titleRow.createCell(3).setCellValue("地址");


        FileOutputStream outputStream = new FileOutputStream("D:/hello.xls");
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

}
