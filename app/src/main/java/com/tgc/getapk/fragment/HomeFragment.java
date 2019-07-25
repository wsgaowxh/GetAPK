package com.tgc.getapk.fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.tgc.getapk.R;
import com.tgc.getapk.adapter.APPAdapter;
import com.tgc.getapk.adapter.HomeViewPagerAdapter;
import com.tgc.getapk.base.App;
import com.tgc.getapk.base.BaseResumeFragment;
import com.tgc.getapk.common.utils.DialogUtils;
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
    @BindView(R.id.path_tv)
    TextView pathTv;
    @BindView(R.id.main_viewpager)
    ViewPager viewPager;
    @BindView(R.id.main_tabs)
    TabLayout tabLayout;
    @BindView(R.id.main_search_rv)
    RecyclerView mainSearchRv;

    private List<ResolveInfo> dataList;
    //    private PackageManager pm;
    private APPAdapter appAdapter;
    private APPAdapter appSearchAdapter;
    private HomePresenter presenter;
    private List<String> permissionsList = new ArrayList<>();
    private static final String[] permissions =
            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private List<String> titles = new ArrayList<>();
    private List<Fragment> views = new ArrayList<>();
    private NonSysAppFragment nonSysAppFragment = new NonSysAppFragment();
    private SysAppFragment sysAppFragment = new SysAppFragment();
    private static int currentPage = 0;
    private ProgressDialog dialogInSearch;

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
//        pm = App.getContext().getPackageManager();
        toolbar.setOnMenuItemClickListener(this);
//        presenter.load();
    }

    @Override
    protected void setupView() {
        setupToolbar(R.string.app_name, toolbar, 0);
        toolbar.getMenu().clear();
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.menu_home);
        SearchView searchView = (SearchView) toolbar.getMenu()
                .findItem(R.id.menu_search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        mainSearchRv.setVisibility(View.INVISIBLE);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = 2;
                tabLayout.setVisibility(View.INVISIBLE);
                viewPager.setVisibility(View.INVISIBLE);
                mainSearchRv.setVisibility(View.VISIBLE);
                loadSearch();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialogInSearch = DialogUtils.alertHasReturn(getContext());
                presenter.getSearchAppList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        toolbar.getMenu().getItem(0).setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                tabLayout.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                mainSearchRv.setVisibility(View.INVISIBLE);
                currentPage = viewPager.getCurrentItem();
                return true;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        currentPage = 0;
        checkPermissions();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void onStop() {
        super.onStop();
        titles.clear();
        views.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        nonSysAppFragment = null;
        sysAppFragment = null;
    }

    private void loadSearch() {
        ArrayList<ResolveInfo> dataList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(App.getContext(), LinearLayoutManager.VERTICAL, false);
        appSearchAdapter = new APPAdapter(App.getContext(), dataList, App.getInstance().getPackageManager());
        mainSearchRv.setAdapter(appSearchAdapter);
        mainSearchRv.setLayoutManager(linearLayoutManager);

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(mainSearchRv.getContext(), R.anim.layout_fall_down);
        mainSearchRv.setLayoutAnimation(animation);
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
            load();
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
                if (grantResults.length == 0) {
                    DialogUtils.alertFinishNoTitle(getContext(), getString(R.string.tips_666), getActivity());
                    return;
                }
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        getActivity().finish();
                        return;
                    }
                }
                load();
                break;
            default:
                break;
        }
    }

    public void load() {
        presenter.rule();
        initViewPager();
        HomeViewPagerAdapter pagerAdapter =
                new HomeViewPagerAdapter(getChildFragmentManager(), titles, views);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        if (loadingPb != null) {
//            loadingPb.setVisibility(View.GONE);
//        }
//        if (recyclerView != null) {
//            recyclerView.setVisibility(View.VISIBLE);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(App.getContext(), LinearLayoutManager.VERTICAL, false);
//            appAdapter = new APPAdapter(App.getContext(), dataList, pm);
//            recyclerView.setAdapter(appAdapter);
//            recyclerView.setLayoutManager(linearLayoutManager);
//        }
        pathTv.setText(getResources().getString(R.string.back_path) + " " + PreferencesHelper.getPath());
    }

    private void initViewPager() {
        titles.clear();
        views.clear();
        titles.add(getString(R.string.non_sys_app));
        titles.add(getString(R.string.sys_app));
        views.add(nonSysAppFragment);
        views.add(sysAppFragment);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        appAdapter = getAppAdapter();
        dataList = getDataList();
        switch (item.getItemId()) {
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
                if (appAdapter == null) {
                    Snackbar.make(getRootView(), R.string.wait, Snackbar.LENGTH_LONG).show();
                    break;
                }
                appAdapter.selectAll();
                break;
            case R.id.menu_anti_all:
                if (appAdapter == null) {
                    Snackbar.make(getRootView(), R.string.wait, Snackbar.LENGTH_LONG).show();
                    break;
                }
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

    private APPAdapter getAppAdapter() {
        switch (currentPage) {
            case 0:
                return nonSysAppFragment.getAdapter();
            case 1:
                return sysAppFragment.getAdapter();
            case 2:
                return appSearchAdapter;
            default:
                return nonSysAppFragment.getAdapter();
        }
    }

    private List<ResolveInfo> getDataList() {
        switch (currentPage) {
            case 0:
                return nonSysAppFragment.getDataList();
            case 1:
                return sysAppFragment.getDataList();
            case 2:
                return appSearchAdapter.getData();
            default:
                return nonSysAppFragment.getDataList();
        }
    }

    @Override
    protected void onFragmentResume() {
        pathTv.setText(getResources().getString(R.string.back_path) + " " + PreferencesHelper.getPath());
    }

    @Override
    public void load(ArrayList<ResolveInfo> dataList) {
        appSearchAdapter.setData(dataList);
        mainSearchRv.scheduleLayoutAnimation();
        dialogInSearch.dismiss();
    }

    @Override
    public void showRule() {
        DialogUtils.rule(getContext(), R.string.rule, getActivity());
    }
}
