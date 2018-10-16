package com.content.dao;

import com.content.model.LoadRecord;
import com.content.model.LoadRecordExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoadRecordMapper {
    long countByExample(LoadRecordExample example);

    int deleteByExample(LoadRecordExample example);

    int deleteByPrimaryKey(Integer fileId);

    int insert(LoadRecord record);

    int insertSelective(LoadRecord record);

    List<LoadRecord> selectByExample(LoadRecordExample example);

    LoadRecord selectByPrimaryKey(Integer fileId);

    int updateByExampleSelective(@Param("record") LoadRecord record, @Param("example") LoadRecordExample example);

    int updateByExample(@Param("record") LoadRecord record, @Param("example") LoadRecordExample example);

    int updateByPrimaryKeySelective(LoadRecord record);

    int updateByPrimaryKey(LoadRecord record);
}