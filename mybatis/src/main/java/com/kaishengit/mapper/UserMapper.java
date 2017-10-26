package com.kaishengit.mapper;

import com.kaishengit.entity.User;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("SELECT * from t_user where id = #{userId}")
    @Results(value = {
            @Result(column = "id",property = "id"),
            @Result(column = "user_name",property = "userName"),
            @Result(column = "address",property = "address"),
            @Result(column = "password",property = "password"),
            @Result(column = "dept_id",property = "deptId"),
            @Result(column = "dept_id", property = "dept",one = @One(select = "com.kaishengit.mapper.DeptMapper.findById")),
            @Result(column = "id",property = "tagList",many = @Many(select = "com.kaishengit.mapper.TagMapper.findByUserId"))
    })
    User findById(Integer userId);
    User findByIdWithTag(Integer userId);

}
