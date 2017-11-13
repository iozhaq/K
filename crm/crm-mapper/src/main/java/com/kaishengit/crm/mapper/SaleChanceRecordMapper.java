package com.kaishengit.crm.mapper;

import com.kaishengit.crm.entity.SaleChanceRecord;
import java.util.List;

import com.kaishengit.crm.example.SaleChanceRecordExample;
import org.apache.ibatis.annotations.Param;

public interface SaleChanceRecordMapper {
    long countByExample(SaleChanceRecordExample example);

    int deleteByExample(SaleChanceRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SaleChanceRecord record);

    int insertSelective(SaleChanceRecord record);

    List<SaleChanceRecord> selectByExampleWithBLOBs(SaleChanceRecordExample example);

    List<SaleChanceRecord> selectByExample(SaleChanceRecordExample example);

    SaleChanceRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SaleChanceRecord record, @Param("example") SaleChanceRecordExample example);

    int updateByExampleWithBLOBs(@Param("record") SaleChanceRecord record, @Param("example") SaleChanceRecordExample example);

    int updateByExample(@Param("record") SaleChanceRecord record, @Param("example") SaleChanceRecordExample example);

    int updateByPrimaryKeySelective(SaleChanceRecord record);

    int updateByPrimaryKeyWithBLOBs(SaleChanceRecord record);

    int updateByPrimaryKey(SaleChanceRecord record);
}