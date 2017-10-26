package com.kaishengit;

import com.kaishengit.entity.User;
import com.kaishengit.mapper.UserMapper;
import com.kaishengit.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class CacheTestCase {

    @Test
    public void levelOneCache() {

        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = userMapper.findById(1);

        //System.out.println(user);

        sqlSession.close();

        //-------------------------------------------------------------------

        SqlSession sqlSession2 = MyBatisUtil.getSqlSession();
        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);

        User user2 = userMapper2.findById(1);

        System.out.println(user2);

        sqlSession2.close();

    }


}
