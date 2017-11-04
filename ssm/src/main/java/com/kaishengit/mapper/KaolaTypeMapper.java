package com.kaishengit.mapper;

import com.kaishengit.entity.KaolaType;
import com.kaishengit.entity.KaolaTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface KaolaTypeMapper {
    long countByExample(KaolaTypeExample example);

    int deleteByExample(KaolaTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(KaolaType record);

    int insertSelective(KaolaType record);

    List<KaolaType> selectByExample(KaolaTypeExample example);

    KaolaType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") KaolaType record, @Param("example") KaolaTypeExample example);

    int updateByExample(@Param("record") KaolaType record, @Param("example") KaolaTypeExample example);

    int updateByPrimaryKeySelective(KaolaType record);

    int updateByPrimaryKey(KaolaType record);
}