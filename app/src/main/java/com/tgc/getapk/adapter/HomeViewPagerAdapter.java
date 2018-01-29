package com.tgc.getapk.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by tgc on 2018/1/29.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    private List<String> titles;
    private List<Fragment> views;

    public HomeViewPagerAdapter(FragmentManager fm, List<String> titles, List<Fragment> views) {
        super(fm);
        this.titles = titles;
        this.views = views;
    }

    @Override
    public Fragment getItem(int position) {
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
