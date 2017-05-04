package com.studio.p2pproject.fragment;

import java.util.HashMap;

/**
 * fragment的工厂类,生产fragment
 */
public class FragmentFactory {
    private static HashMap<Integer, BaseFragment> sFragmentMap = new HashMap<>();

    public static BaseFragment createFragment(int position) {
        BaseFragment fragment = sFragmentMap.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new TouZiFragment();
                    break;
                case 2:
                    fragment = new ZiChanFragment();
                    break;
                case 3:
                    fragment = new MoreFragment();
                    break;
            }
            sFragmentMap.put(position, fragment);
        }
        return fragment;
    }
}
