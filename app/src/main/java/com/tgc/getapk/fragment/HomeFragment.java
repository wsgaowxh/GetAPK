package com.tgc.getapk.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tgc.getapk.R;
import com.tgc.getapk.adapter.APPAdapter;
import com.tgc.getapk.base.App;
import com.tgc.getapk.base.BaseResumeFragment;
import com.tgc.getapk.common.utils.PreferencesHelper;
import com.tgc.getapk.mvp.presenter.HomePresenter;
import com.tgc.getapk.mvp.view.HomeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends BaseResumeFragment implements HomeView,
        Toolbar.OnMenuItemClickListener {
    public static final String TAG = "HomeFragment";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_rv)
    RecyclerView recyclerView;
    @BindView(R.id.loading_pb)
    ProgressBar loadingPb;
    @BindView(R.id.path_tv)
    TextView pathTv;

    private List<ResolveInfo> dataList;
    private PackageManager pm;
    private APPAdapter appAdapter;
    private HomePresenter presenter;
    private List<String> permissionsList = new ArrayList<>();
    private static final String[] permissions =
            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};


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
    public void onStart() {
        super.onStart();
        checkPermissions();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    public void checkPermissions() {
        permissionsList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(App.getContext(), permissions[i])
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permissions[i]);
            }
        }
        if (permissionsList.isEmpty()) {
            presenter.load();
        } else {
            String[] permissionToCheck = permissionsList.toArray(new String[permissionsList.size()]);
            HomeFragment.this.requestPermissions(permissionToCheck, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        getActivity().finish();
                        return;
                    }
                }
                presenter.load();
                break;
            default:
                break;
        }
    }

    //    @Override
//    public void onTitleClick(int id) {
//        presenter.startCopy(id, dataList, getContext());
//    }

    @Override
    public void load(List<ResolveInfo> dataList) {
        this.dataList = dataList;
        loadingPb.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(App.getContext(), LinearLayoutManager.VERTICAL, false);
        appAdapter = new APPAdapter(App.getContext(), dataList, pm);
//        appAdapter.setOnTitleClickListener(this);
        recyclerView.setAdapter(appAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        pathTv.setText(getResources().getString(R.string.back_path) + " " + PreferencesHelper.getPath());
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
                if (appAdapter == null) {
                    Snackbar.make(getRootView(), R.string.wait, Snackbar.LENGTH_LONG).show();
                    break;
                }
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
            case R.id.menu_setting:
                addFragment(new SettingFragment(), SettingFragment.TAG, true);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onFragmentResume() {
        pathTv.setText(getResources().getString(R.string.back_path) + " " + PreferencesHelper.getPath());
    }
}
