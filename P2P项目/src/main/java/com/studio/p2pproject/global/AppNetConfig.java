package com.studio.p2pproject.global;

/**
 * 配置程序中所有接口的请求地址
 */
public class AppNetConfig {
    public static final String HOST = "192.168.1.101";
    public static final String BASEURL = "http://" + HOST + ":8080/P2PInvest";
    public static final String LOGIN = BASEURL + "login";
    public static final String PRODUCT = BASEURL + "product";
    public static final String INDEX = BASEURL + "index";

    //.......实际项目中有多少个接口就配置多少个
}
