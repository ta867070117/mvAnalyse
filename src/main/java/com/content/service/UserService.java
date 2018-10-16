package com.content.service;

import com.content.PO.BasePO;

/**
 * Description
 * Author: wanglei
 * Version:1.0
 * Create Data Time: 14:25 2018/8/30
 */
public interface UserService {

    public void addUser(BasePO basePO);

    int getExitOpenId(String openid);
}
