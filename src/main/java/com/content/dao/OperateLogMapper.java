package com.content.dao;

import com.content.model.OperateLog;
import com.content.model.OperateLogExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OperateLogMapper {
    long countByExample(OperateLogExample example);

    int deleteByExample(OperateLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OperateLog record);

    int insertSelective(OperateLog record);

    List<OperateLog> selectByExample(OperateLogExample example);

    OperateLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OperateLog record, @Param("example") OperateLogExample example);

    int updateByExample(@Param("record") OperateLog record, @Param("example") OperateLogExample example);

    int updateByPrimaryKeySelective(OperateLog record);

    int updateByPrimaryKey(OperateLog record);
}