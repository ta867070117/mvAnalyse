package com.content.dao;

import com.content.PO.BasePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Description
 * Author: wanglei
 * Version:1.0
 * Create Data Time: 14:18 2018/8/30
 */
@Mapper
public interface UserMapper {

    void addUser(BasePO basePO);

    int getExitOpenId(String openid);
}
