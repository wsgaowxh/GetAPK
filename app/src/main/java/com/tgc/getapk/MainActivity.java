package com.tgc.getapk;

import com.tgc.getapk.base.BaseActivity;
import com.tgc.getapk.fragment.HomeFragment;


public class MainActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_content;
    }

    @Override
    protected void init() {
        replaceFragment(new HomeFragment(), R.id.content, false);
    }

    @Override
    protected void setupView() {

    }
}
