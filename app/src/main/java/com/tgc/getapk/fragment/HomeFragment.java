package com.tgc.getapk.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tgc.getapk.R;
import com.tgc.getapk.adapter.APPAdapter;
import com.tgc.getapk.base.App;
import com.tgc.getapk.base.BaseFragment;
import com.tgc.getapk.mvp.presenter.HomePresenter;
import com.tgc.getapk.mvp.view.HomeView;

import java.util.List;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * A simple {@link Fragment} subclass.
 */

@RuntimePermissions
public class HomeFragment extends BaseFragment implements HomeView,
        Toolbar.OnMenuItemClickListener{
    public static final String TAG = "HomeFragment";

    @BindView(R.id.main_rv)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<ResolveInfo> dataList;
    private PackageManager pm;
    private APPAdapter appAdapter;
    private HomePresenter presenter;

    @Override
    protected void initPresenter() {
        presenter = new HomePresenter();
        presenter.attachView(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        pm = App.getContext().getPackageManager();
        toolbar.setOnMenuItemClickListener(this);
//        presenter.load();
    }

    @Override
    protected void setupView() {
        setupToolbar(R.string.app_name, toolbar, 0);
        toolbar.getMenu().clear();
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.menu_home);
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeFragmentPermissionsDispatcher.loadWithCheck(this);
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void load() {
        presenter.load();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void toEnd() {
        getActivity().finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        HomeFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

//    @Override
//    public void onTitleClick(int id) {
//        presenter.startCopy(id, dataList, getContext());
//    }

    @Override
    public void load(List<ResolveInfo> dataList) {
        this.dataList = dataList;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(App.getContext(), LinearLayoutManager.VERTICAL, false);
        appAdapter = new APPAdapter(App.getContext(), dataList, pm);
//        appAdapter.setOnTitleClickListener(this);
        recyclerView.setAdapter(appAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.menu_setting:
//                break;
//            case R.id.menu_share:
//                break;
//            case R.id.menu_about:
//                break;
            case R.id.menu_pick:
                List<Integer> checkList = appAdapter.getCheckList();
                if (checkList == null || checkList.isEmpty()) {
                    Snackbar.make(getRootView(), R.string.select_null, Snackbar.LENGTH_LONG).show();
                    break;
                }
                presenter.startCopyWithList(checkList, dataList, getContext());
                break;
            case R.id.menu_all:
                appAdapter.selectAll();
                break;
            case R.id.menu_anti_all:
                appAdapter.antiSelectAll();
                break;
            default:
                break;
        }
        return true;
    }
}
