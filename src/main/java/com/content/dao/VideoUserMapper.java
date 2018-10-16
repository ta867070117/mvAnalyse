package com.content.dao;

import com.content.model.VideoUser;
import com.content.model.VideoUserExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VideoUserMapper {
    long countByExample(VideoUserExample example);

    int deleteByExample(VideoUserExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(VideoUser record);

    int insertSelective(VideoUser record);

    List<VideoUser> selectByExample(VideoUserExample example);

    VideoUser selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") VideoUser record, @Param("example") VideoUserExample example);

    int updateByExample(@Param("record") VideoUser record, @Param("example") VideoUserExample example);

    int updateByPrimaryKeySelective(VideoUser record);

    int updateByPrimaryKey(VideoUser record);

    void updateAttendNum(String openId);
}