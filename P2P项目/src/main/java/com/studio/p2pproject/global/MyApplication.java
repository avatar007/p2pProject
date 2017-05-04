package com.studio.p2pproject.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

/**
 * 作者: 木水 于 2017/4/29 20:25
 * 邮箱:123421919@qq.com
 */
public class MyApplication extends Application {
    public static Context sContext = null;
    public static Handler sHandler = null;
    public static Thread mainThread = null;
    public static int threadId = 0;

    @Override
    public void onCreate() {
        sContext = getApplicationContext();
        sHandler = new Handler();
        mainThread = Thread.currentThread();
        threadId = Process.myTid();
        //CrashHandler.getInstance().init(this);//全局中注册异常捕获器
    }
}
