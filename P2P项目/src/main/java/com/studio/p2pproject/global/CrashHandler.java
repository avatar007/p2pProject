package com.studio.p2pproject.global;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.os.Process;
import android.widget.Toast;

/**
 * 全局异常的捕获
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler mCrashHandler = null;  //要设计成单例模式
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (mCrashHandler == null) {
            mCrashHandler = new CrashHandler();
        }
        return mCrashHandler;
    }

    /**
     * 初始化异常处理信息,要在application中调用
     */
    public void init(Context context) {
        this.mContext = context;
        mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();//获取默认的
        Thread.setDefaultUncaughtExceptionHandler(this);//设置自己定义的异常捕获器
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (isHandler(throwable)) {
            handlerException(thread, throwable);//自己处理
        } else {//系统自己处理
            mDefaultUncaughtExceptionHandler.uncaughtException(thread, throwable);
        }
    }

    /**
     * 收集异常信息,发送到后台
     */
    private void collectionException(Throwable throwable) {
        //收集手机制造商,模式,SDK版本信息
        String phoneInfo = Build.PRODUCT + ",手机型号:"+Build.MODEL +",系统版本:"+ Build.VERSION.SDK_INT;
        String errorInfo = throwable.getMessage();//获取异常信息
        //把信息发送给服务器后台...............省略
        //这里提示下
        System.out.println("手机信息 = " + phoneInfo +"\n"+ "错误信息" + errorInfo);
    }

    /**
     * 判断是否需要自己处理异常
     */
    private boolean isHandler(Throwable throwable) {
        return true ? throwable != null : throwable == null;
    }

    /**
     * 自己处理异常信息
     */
    private void handlerException(Thread thread, Throwable throwable) {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "系统出现异常,3秒后退出", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        collectionException(throwable);
        try {
            thread.sleep(3000);
            AppStackManager.getInstance().removeAll();//移除任务栈的所有activity
            Process.killProcess(Process.myPid());//杀死app进程
            System.exit(0);//退出虚拟机
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
