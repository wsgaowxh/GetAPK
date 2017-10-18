package com.xusangbo.basemoudle.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xusangbo.basemodule.R;
import com.xusangbo.basemoudle.baserx.RxManager;
import com.xusangbo.basemoudle.dialog.LoadingDialog;
import com.xusangbo.basemoudle.utils.TUtil;
import com.xusangbo.basemoudle.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by boxu on 2017/4/1.
 */

public abstract class BaseFragment<T extends BasePresenter, E extends BaseModel> extends SupportFragment {
    private static final String TAG = "BaseFragment";
    public Toolbar mToolbar;
    public TextView title;
    public ImageView back;
    public TextView tv_menu;
    public ImageView iv_menu;
    protected View rootView;
    public T mPresenter;
    public E mModel;
    public RxManager mRxManager;
    private Unbinder bind;
    protected BaseActivity mActivity;

    public BaseFragment() { /* compiled code */ }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (null != getArguments()) {
            getBundleExtras(getArguments());
        }
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);
        mRxManager = new RxManager();
        bind = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this.getActivity();
        }
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        if (null != mToolbar) {
            initTitle();
        }
        initTitle();
        initPresenter();
        initView();
    }

    /**
     * 获取bundle信息
     *
     * @param bundle
     */
    protected abstract void getBundleExtras(Bundle bundle);

    //获取布局文件
    protected abstract int getLayoutResource();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    protected abstract void initView();

    protected void initTitle() {
        title = (TextView) rootView.findViewById(R.id.toolbar_title);
        iv_menu = (ImageView) rootView.findViewById(R.id.toolbar_iv_menu);
        back = (ImageView) rootView.findViewById(R.id.toolbar_back);
        tv_menu = (TextView) rootView.findViewById(R.id.toolbar_tv_menu);
    }

    protected void showLoadingDialog() {
        LoadingDialog.showLoadingDialog(getActivity());
    }

    protected void cancelLoadingDialog() {
        LoadingDialog.cancelLoadingDialog();
    }

    //Toast显示
    protected void showShortToast(String string) {
        ToastUtils.showShortToast(getActivity(), string);
    }

    protected void showShortToast(int stringId) {
        ToastUtils.showShortToast(getActivity(), stringId);
    }

    protected void showLongToast(String string) {
        ToastUtils.showShortToast(getActivity(), string);
    }

    protected void showLongToast(int stringId) {
        ToastUtils.showShortToast(getActivity(), stringId);
    }

    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivityForResult(intent, requestCode);
    }

    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
        if (mPresenter != null)
            mPresenter.onDestroy();
        mRxManager.clear();
    }

    public void setupToolbar(String title, Toolbar toolbar, @DrawableRes int icon) {
        toolbar.setTitle(title);
        if (icon != 0) toolbar.setNavigationIcon(icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    public void setupToolbar(@StringRes int titleRes, Toolbar toolbar, @DrawableRes int icon) {
        setupToolbar(getString(titleRes), toolbar, icon);
    }

    public void setupToolbar(String title, Toolbar toolbar) {
        setupToolbar(title, toolbar, 0);
    }

    public void popFragment() {
        if (mActivity == null) {
            return;
        }

        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    public void replaceFragment(Fragment fragment, String tag, boolean addToBackStack,
                                @IdRes int id, Bundle bundle) {
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
        transaction.replace(id, fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    public void replaceFragment(Fragment fragment, String tag, boolean addToBackStack, @IdRes int id) {
        replaceFragment(fragment, tag, addToBackStack, id, null);
    }

}
