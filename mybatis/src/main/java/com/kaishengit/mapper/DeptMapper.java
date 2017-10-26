package com.kaishengit.mapper;

import com.kaishengit.entity.Dept;
import org.apache.ibatis.annotations.Select;

public interface DeptMapper {

    @Select("select * from t_dept where id = #{id}")
    Dept findById(Integer id);
}
