package com.content.PO;

import java.io.Serializable;

/**
 * Description
 * Author: wanglei
 * Version:1.0
 * Create Data Time: 14:28 2018/8/30
 */
public class BasePO implements Serializable {

    private String openId;

    private Integer userId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "BasePO{" +
                "openId='" + openId + '\'' +
                ", userId=" + userId +
                '}';
    }
}
