package com.studio.p2pproject.fragment;

import android.widget.TextView;

import com.studio.p2pproject.R;

import butterknife.BindView;

/**
 * 投资页面fragment
 */
public class TouZiFragment extends BaseFragment {


    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_touzi;
    }

    @Override
    public void initData() {
        mTvTitle.setText("我要投资");
    }
}
