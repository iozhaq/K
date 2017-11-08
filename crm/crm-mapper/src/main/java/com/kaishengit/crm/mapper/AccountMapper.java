package com.kaishengit.crm.mapper;

import java.util.List;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.example.AccountExample;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper {
    long countByExample(AccountExample example);

    int deleteByExample(AccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    int insertSelective(Account record);

    List<Account> selectByExample(AccountExample example);

    Account selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByExample(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    List<Account> findByDeptId(@Param("accountName") String accountName,
            @Param("deptId") Integer deptId,
                               @Param("start") Integer start,
                               @Param("length") Integer length);

    Long countByDeptId(@Param("deptId") Integer deptId);
}