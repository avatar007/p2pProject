package com.studio.p2pproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.p2pproject.util.UiUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * fragment的基类
 */
public abstract class BaseFragment extends Fragment {
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = UiUtils.inflate(getLayoutId());
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    public abstract int getLayoutId();//子类加载布局

    public abstract void initData();//子类的加载数据

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
