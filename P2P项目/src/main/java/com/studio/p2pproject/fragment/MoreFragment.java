package com.studio.p2pproject.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.p2pproject.R;

import butterknife.BindView;

/**
 * 更多页面fragment
 */
public class MoreFragment extends BaseFragment {

    @BindView(R.id.iv_left)
    ImageView mIvLeft;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_right)
    ImageView mIvRight;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_more;
    }

    @Override
    public void initData() {
        mTvTitle.setText("更多");
        mIvRight.setVisibility(View.VISIBLE);
        mIvLeft.setVisibility(View.VISIBLE);
    }

}
