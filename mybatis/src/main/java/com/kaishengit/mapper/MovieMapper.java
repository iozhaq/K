package com.kaishengit.mapper;

import com.kaishengit.entity.Movie;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MovieMapper {

    List<Movie> find(@Param("title") String title);
    List<Movie> findByParam(Map<String,Object> searchParam);
    List<Movie> findByIdList(@Param("idList") List<Integer> idList);

}
