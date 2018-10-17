package com.content.utils;

public class App {

    private static final String BASE_URL = "https://aweme.snssdk.com/aweme/v1/play/?video_id=%s";

    public static void main(String[] args) { String shareUrl = "http://v.douyin.com/RJFHQR/";
        String douyin = douyin(shareUrl);
        System.out.println(douyin);
    }
    public static String douyin(String url){
        try {
            String html = HttpClientUtil.doGet(url);
            int len = html.indexOf("video_id");
            String end = html.substring(len);
            int indexOf = end.indexOf("\",");
            String videoId = end.substring(9, indexOf);
            videoId = videoId.replace("\\u0026", "&");
            return String.format(BASE_URL, videoId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}