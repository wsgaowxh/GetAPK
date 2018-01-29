package com.tgc.getapk.fragment;


import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.tgc.getapk.R;
import com.tgc.getapk.adapter.APPAdapter;
import com.tgc.getapk.base.App;
import com.tgc.getapk.base.BaseFragment;
import com.tgc.getapk.mvp.presenter.SysAppPresenter;
import com.tgc.getapk.mvp.view.SysAppView;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SysAppFragment extends BaseFragment implements SysAppView {

    @BindView(R.id.main_rv)
    RecyclerView recyclerView;
    @BindView(R.id.loading_pb)
    ProgressBar loadingPb;

    private SysAppPresenter presenter;
    private List<ResolveInfo> dataList;
    private APPAdapter appAdapter;
    private PackageManager pm;

    @Override
    protected int getLayout() {
        return R.layout.fragment_sys_app;
    }

    @Override
    protected void init() {
        pm = App.getContext().getPackageManager();
        presenter.load();
    }

    @Override
    protected void setupView() {

    }

    @Override
    protected void initPresenter() {
        presenter = new SysAppPresenter();
        presenter.attachView(this);
    }

    @Override
    public void load(List<ResolveInfo> dataList) {
        this.dataList = dataList;
        if (loadingPb != null) {
            loadingPb.setVisibility(View.GONE);
        }
        if (recyclerView != null) {
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(App.getContext(), LinearLayoutManager.VERTICAL, false);
            appAdapter = new APPAdapter(App.getContext(), dataList, pm);
            recyclerView.setAdapter(appAdapter);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    public APPAdapter getAdapter() {
        return appAdapter;
    }

    public List<ResolveInfo> getDataList() {
        return dataList;
    }
}
