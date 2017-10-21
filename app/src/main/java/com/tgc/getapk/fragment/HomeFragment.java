package com.tgc.getapk.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

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
public class HomeFragment extends BaseFragment implements APPAdapter.OnTitleClickListener, HomeView {
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
//        presenter.load();
    }

    @Override
    protected void setupView() {
        setupToolbar(R.string.app_name, toolbar, 0);
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

    @Override
    public void onTitleClick(int id) {
        presenter.startCopy(id, dataList, getContext());
    }

    @Override
    public void load(List<ResolveInfo> dataList) {
        this.dataList = dataList;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(App.getContext(), LinearLayoutManager.VERTICAL, false);
        appAdapter = new APPAdapter(App.getContext(), dataList, pm);
        appAdapter.setOnTitleClickListener(this);
        recyclerView.setAdapter(appAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
