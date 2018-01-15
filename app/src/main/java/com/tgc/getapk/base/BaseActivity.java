package com.tgc.getapk.base;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by imco on 11/13/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

//    private Unbinder mUnbinder;

    protected abstract int getLayout();

    protected abstract void init();

    protected abstract void setupView();


    protected void showToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
//        mUnbinder = ButterKnife.bind(this);
        setupView();
        init();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mUnbinder.unbind();
    }

    public DrawerLayout getDrawerLayout() {
        return null;
    }

    public void replaceFragment(Fragment fragment, int contentId, boolean addToBackStack) {
        replaceFragment(fragment, contentId, addToBackStack, null);
    }

    public void replaceFragment(Fragment fragment, int contentId, boolean addToBackStack, Bundle bundle) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        if (null != bundle) {
            fragment.setArguments(bundle);
        }
        transaction.replace(contentId, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
