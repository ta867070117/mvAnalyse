package com.content.utils;


/**
 * @author wanglei
 */
public class DecodeVideo {

    private String cover;
    private String playAddr;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPlayAddr() {
        return playAddr;
    }

    public void setPlayAddr(String playAddr) {
        this.playAddr = playAddr;
    }

    @Override
    public String toString() {
        return "DecodeVideo [cover=" + cover + ", playAddr=" + playAddr + "]";
    }
}