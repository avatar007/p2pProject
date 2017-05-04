package com.studio.p2pproject.fragment;

import android.widget.TextView;

import com.studio.p2pproject.R;
import com.studio.p2pproject.view.CustomProgress;

import butterknife.BindView;

/**
 * 首页fragment
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.cp_progress1)
    CustomProgress mCpProgress1;
    @BindView(R.id.cp_progress2)
    CustomProgress mCpProgress2;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    public void initData() {
        mTvTitle.setText("首页");
        /*new Thread() {
            @Override
            public void run() {
                int temp = 0;
                while (temp <= progress) {
                    mCpProgress1.setProgress(temp);
                    temp++;
                    SystemClock.sleep(20);
                }
            }
        }.start();*/
        mCpProgress1.setProgress(77);
    }
}
