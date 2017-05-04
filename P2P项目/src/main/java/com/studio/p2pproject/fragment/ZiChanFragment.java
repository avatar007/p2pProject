package com.studio.p2pproject.fragment;

import android.widget.TextView;

import com.studio.p2pproject.R;

import butterknife.BindView;

/**
 * 资产页面fragment
 */
public class ZiChanFragment extends BaseFragment {


    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_zichan;
    }

    @Override
    public void initData() {
        mTvTitle.setText("我的资产");
    }

}
