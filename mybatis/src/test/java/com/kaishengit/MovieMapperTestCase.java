package com.kaishengit;

import com.kaishengit.entity.Movie;
import com.kaishengit.mapper.MovieMapper;
import com.kaishengit.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieMapperTestCase {

    private SqlSession sqlSession;
    private MovieMapper movieMapper;

    @Before
    public void init() {
        sqlSession = MyBatisUtil.getSqlSession();
        movieMapper = sqlSession.getMapper(MovieMapper.class);
    }

    @After
    public void close() {
        sqlSession.close();
    }

    @Test
    public void find() {
        String title = "%老友记%";
        List<Movie> movieList = movieMapper.find(title);
        System.out.println("Movie size: >>>>>>>>>>>>>>>>" + movieList.size());
    }

    @Test
    public void findByParam() {
        Map<String,Object> searchParam = new HashMap<>();
        searchParam.put("title","%老友记%");
        //searchParam.put("director","%大卫·克拉尼%");


        List<Movie> movieList = movieMapper.findByParam(searchParam);
        for(Movie movie : movieList) {
            System.out.println(movie);
        }
    }

    @Test
    public void findByIdList() {
        List<Integer> idList = Arrays.asList(101,102,103);
        List<Movie> movieList = movieMapper.findByIdList(idList);
        for(Movie movie : movieList) {
            System.out.println(movie);
        }
    }
}
