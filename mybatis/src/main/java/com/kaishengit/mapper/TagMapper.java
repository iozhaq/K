package com.kaishengit.mapper;

import com.kaishengit.entity.Tag;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@CacheNamespace // <cache/>
public interface TagMapper {

    @Select("select * from t_tag where id  in (select tag_id from user_tag where user_id = #{userId})")
    List<Tag> findByUserId(Integer userId);

}
