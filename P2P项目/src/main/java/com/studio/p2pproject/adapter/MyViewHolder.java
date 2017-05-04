package com.studio.p2pproject.adapter;

import android.view.View;

/**
 * 作者: 某某某 于 2017/5/3 11:24
 * 概述: ViewHolder基类
 */
public abstract class MyViewHolder<T> {

    private View mRootView;
    private T data;

    public MyViewHolder() {
        mRootView = initView();
        mRootView.setTag(this);
    }

    public void setData(T data) {
        this.data = data;
        refreshView(data);
    }

    public T getData() {
        return data;
    }

    public abstract View initView();//初始化布局
    protected abstract void refreshView(T data);//根据数据刷新界面
}