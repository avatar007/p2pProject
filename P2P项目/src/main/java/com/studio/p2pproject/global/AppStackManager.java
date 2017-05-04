package com.studio.p2pproject.global;

import android.app.Activity;

import java.util.Stack;

/**
 * AppManager要单例
 * 统一app程序中所有Activity的栈管理
 */
public class AppStackManager {
    public Stack<Activity> sActivityStack = new Stack<>();
    private static AppStackManager mManager = null;

    private AppStackManager() {
    }

    public static AppStackManager getInstance() {
        if (mManager == null) {
            mManager = new AppStackManager();
        }
        return mManager;
    }

    //添加activity到任务栈中
    public void addActivity(Activity activity) {
        sActivityStack.add(activity);
    }

    //从任务栈中移除activity(遍历中删除对象,倒着遍历即可,也可使用迭代器遍历)
    public void removeActivity(Activity activity) {

        for (int i = sActivityStack.size() - 1; i >= 0; i--) {
            //判断是否是同一个activity,是的话先结束再移除
            Activity temp = sActivityStack.get(i);
            if (temp.getClass().equals(activity.getClass())) {
                temp.finish();
                sActivityStack.remove(i);
                break;
            }
        }
        //迭代器遍历
        /*Iterator<Activity> iterator = sActivityStack.iterator();
        while (iterator.hasNext()){
            Activity temp = iterator.next();
            if (temp.getClass().equals(activity.getClass())){
                temp.finish();
                iterator.remove();
                break;
            }
        }*/

    }

    //移除所有任务栈中的activity
    public void removeAll() {
        for (int i = sActivityStack.size() - 1; i >= 0; i--) {
            sActivityStack.get(i).finish();
            sActivityStack.remove(i);//移除所有
        }
    }

    //获取任务栈中activity个数
    public int getStackSize() {
        return sActivityStack.size();
    }
}
