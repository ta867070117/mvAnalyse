package com.content.service.impl;

import com.content.PO.BasePO;
import com.content.dao.UserMapper;
import com.content.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description
 * Author: wanglei
 * Version:1.0
 * Create Data Time: 14:26 2018/8/30
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(BasePO basePO) {
        userMapper.addUser(basePO);
    }

    @Override
    public int getExitOpenId(String openid) {
        return userMapper.getExitOpenId(openid);
    }
}
