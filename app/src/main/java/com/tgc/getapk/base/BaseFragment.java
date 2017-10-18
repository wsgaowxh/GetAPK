package com.tgc.getapk.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tgc.getapk.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ivan on 17-4-11.
 */

public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;

    /**
     * override method to set fragment layout
     *
     * @return the layout id.
     */
    protected abstract int getLayout();

    /**
     * you can do something in this method, e.g init presenter load data etc.
     */
    protected abstract void init();

    /**
     * setup view in this method
     */
    protected abstract void setupView();

    protected View mRootView;

    private Unbinder mUnbinder;

    private BasePresenter mPresenter;

    protected boolean isNeedOptionMenu() {
        return false;
    }

    protected void setPresenter(BasePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(isNeedOptionMenu());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayout(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        mRootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        setupView();
        init();
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    public void addFragment(BaseFragment fragment, String tag) {
        addFragment(fragment, tag, true, null);
    }

    public void addFragment(Fragment fragment, String tag, boolean addToBackStack, Bundle bundle) {

        if (mActivity == null) {
            return;
        }

        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        transaction.add(R.id.content, fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    public void addFragment(Fragment fragment, String tag, boolean addToBackStack) {
        addFragment(fragment, tag, addToBackStack, null);
    }

    protected void popFragment() {
        if (mActivity == null) {
            return;
        }

        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    protected View getRootView() {
        return mRootView;
    }

    protected void setupToolbar(Toolbar toolbar, String title) {
        setupToolbar(title, toolbar);
    }

    protected void setupToolbar(@StringRes int titleRes, Toolbar toolbar) {
        setupToolbar(getString(titleRes), toolbar);
    }

    protected void setupToolbar(String title, Toolbar toolbar) {
        setupToolbar(title, toolbar, R.drawable.ic_arrow_back_black_24dp);
    }

    protected void setupToolbar(String title, Toolbar toolbar, @DrawableRes int icon) {
        toolbar.setTitle(title);
        if (icon != 0) toolbar.setNavigationIcon(icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    protected void showToast(String toast) {
        Toast.makeText(App.getContext(), toast, Toast.LENGTH_SHORT).show();
    }


    protected void showToast(@StringRes int stringRes) {
        showToast(getString(stringRes));
    }

}
