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
import com.tgc.getapk.mvp.presenter.NonSysAppPresenter;
import com.tgc.getapk.mvp.view.NonSysAppView;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NonSysAppFragment extends BaseFragment implements NonSysAppView {

    @BindView(R.id.main_rv)
    RecyclerView recyclerView;
    @BindView(R.id.loading_pb)
    ProgressBar loadingPb;

    private NonSysAppPresenter presenter;
    private List<ResolveInfo> dataList;
    private APPAdapter appAdapter;
    private PackageManager pm;

    @Override
    protected int getLayout() {
        return R.layout.fragment_non_sys_app;
    }

    @Override
    protected void init() {
        pm = App.getContext().getPackageManager();
    }

    @Override
    protected void setupView() {

    }

    @Override
    protected void initPresenter() {
        presenter = new NonSysAppPresenter();
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
}
