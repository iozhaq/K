package com.kaishengit;

import com.kaishengit.entity.Tag;
import com.kaishengit.entity.User;
import com.kaishengit.mapper.UserMapper;
import com.kaishengit.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserMapperTestCase {

    private SqlSession sqlSession;
    private UserMapper userMapper;

    @Before
    public void init() {
        sqlSession = MyBatisUtil.getSqlSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @After
    public void close() {
        sqlSession.close();
    }

    @Test
    public void findById() {
        User user = userMapper.findById(1);
        System.out.println(user);
        System.out.println(user.getUserName() + " -> " + user.getDept().getDeptName());

        List<Tag> tagList = user.getTagList();

        for(Tag tag : tagList) {
            System.out.println(tag.getTagName());
        }
    }

    @Test
    public void findByIdWithTag() {
        User user = userMapper.findByIdWithTag(1);
        System.out.println(user);

        List<Tag> tagList = user.getTagList();
        for(Tag tag : tagList) {
            System.out.println(tag);
        }
    }


}
