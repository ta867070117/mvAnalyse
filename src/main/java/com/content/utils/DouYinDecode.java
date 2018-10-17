package com.content.utils;


import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * tips：导入jsoup包
 *
 * @author wanglei
 */
@SuppressWarnings("all")
public class DouYinDecode {

    public static void main(String[] args) {
        String url = "http://v.douyin.com/dEVV4j/";// 解析的网址，长短链接都可以
        String playAddr = getURI(NewUrlDecode(urlAnalysisMethod(url))).trim();// 有空白符
        String cover = getCover(urlAnalysisMethod(url)).trim();// 有空白符
        DecodeVideo dv = new DecodeVideo();
        dv.setPlayAddr(playAddr);
        dv.setCover(cover);
        System.out.println(dv);

        /*
         * tips:最后得到的结果 DecodeVideo
         * [cover=https://p3.pstatp.com/large/86920013ac9cb67fe09a.jpg,
         * playAddr=http://v3-dy-z.ixigua.com/d153fa2e0d25730c9a6ffa949e228598/5b2b4b7e/
         * video/m/2203826b87d19034f608c53f014a2f5ece01157576f0000077573318095/]
         */
    }

    public static String urlAnalysisMethod(String url) {
        try {
            if (isContainChinese(url)) {
                url = cuthttpschinese(url);
            }
            if (url.length() < 40) {
                url = getURI(url);
            }
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String NewUrlDecode(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(5000).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println(doc.data());
        Elements elem = doc.getElementsByTag("script");
        String url1 = elem.toString();
        int start = url1.indexOf("playAddr");
        url1 = url1.substring(start);
        int end = url1.indexOf("\",");
        return url1.substring(11, end).replaceAll("playwm", "play");
    }

    // 获取封面，项目要用到
    public static String getCover(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(5000).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Elements elem = doc.getElementsByTag("script");
        String url1 = elem.toString();
        int start = url1.indexOf("cover");
        String str = url1.substring(start);
        int end = str.indexOf("}");
        return str.substring(8, end).replaceAll("\"", "");
    }

    /**
     * @param url
     * @return
     */
    // 获取真实地址
    public static String getURI(String url) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext httpContext = new BasicHttpContext();
        HttpGet httpGet = new HttpGet(url);
        try {
            // 将HttpContext对象作为参数传给execute()方法,则HttpClient会把请求响应交互过程中的状态信息存储在HttpContext中
            HttpResponse response = httpClient.execute(httpGet, httpContext);
            // 获取重定向之后的主机地址信息
            HttpHost targetHost = (HttpHost) httpContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
            // 获取实际的请求对象的URI,即重定向之后的地址
            HttpUriRequest realRequest = (HttpUriRequest) httpContext.getAttribute(ExecutionContext.HTTP_REQUEST);
            return (HttpHost) httpContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST)
                    + ((HttpUriRequest) httpContext.getAttribute(ExecutionContext.HTTP_REQUEST)).getURI().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    /**
     * @param str
     * @return
     */
    // 检查是否有中文
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * @param str
     * @return
     */
    // 截取到http开始的字段
    public static String cuthttpschinese(String str) {
        int start = str.indexOf("http");
        return str.substring(start);
    }
}