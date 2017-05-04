package com.studio.p2pproject.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.studio.p2pproject.R;
import com.studio.p2pproject.fragment.BaseFragment;
import com.studio.p2pproject.fragment.FragmentFactory;
import com.studio.p2pproject.global.AppStackManager;
import com.studio.p2pproject.view.NoScrollViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.vp_content)
    NoScrollViewPager mVpContent;
    @BindView(R.id.tl_tab)
    TabLayout mTlTab;
    private String[] mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AppStackManager.getInstance().addActivity(this);//手动添加到自己维护的任务栈
        initData();
    }

    private void initData() {
        //获取tab文字和图片
        mTabs = getResources().getStringArray(R.array.tab_title);
        int[] icons = {R.drawable.home_icon_selector, R.drawable.touzi_icon_selector,
                R.drawable.me_icon_selector, R.drawable.more_icon_selector};

        //设置数据适配器
        mVpContent.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        //关联tablayout
        mTlTab.setupWithViewPager(mVpContent);
        for (int i = 0; i < mTlTab.getTabCount(); i++) {
            mTlTab.getTabAt(i).setIcon(icons[i]);//添加图片
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return mTabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs[position];
        }


    }

}
