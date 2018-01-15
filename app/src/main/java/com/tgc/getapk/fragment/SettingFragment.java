package com.tgc.getapk.fragment;


import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;
import com.tgc.getapk.R;
import com.tgc.getapk.base.BaseFragment;
import com.tgc.getapk.common.C;
import com.tgc.getapk.common.utils.DialogUtils;
import com.tgc.getapk.common.utils.PreferencesHelper;
import com.tgc.getapk.common.utils.Utils;
import com.tgc.getapk.mvp.view.SettingsView;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment implements SettingsView,
        AdapterView.OnItemClickListener {

    Toolbar toolbar;
    ListView lvSetting;

    public static final String TAG = "SettingFragment";

    @Override
    protected int getLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void init() {
        lvSetting.setOnItemClickListener(this);
    }

    @Override
    protected void setupView() {
        toolbar = (Toolbar) getRootView().findViewById(R.id.toolbar);
        lvSetting = (ListView) getRootView().findViewById(R.id.lv_setting);
        setupToolbar(R.string.setting, toolbar, 0);
        toolbar.getMenu().clear();
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                new LFilePicker().withSupportFragment(this)
                        .withRequestCode(C.SELECT_SUCCESS)
                        .withTitle(getResources().getString(R.string.dir_select))
                        .withIconStyle(Constant.ICON_STYLE_BLUE)
                        .withMutilyMode(false)
                        .withChooseMode(false)
                        .withBackgroundColor("#00AAFF")
                        .start();
                break;
            case 1:
                String version = Utils.getVersion();
                if (version == null) {
                    Snackbar.make(getRootView(), R.string.about_app, Snackbar.LENGTH_SHORT).show();
                    break;
                }
                version = "Version: V" + version;
                DialogUtils.alert(getContext(), R.string.about, version + "\n"
                        + getResources().getString(R.string.about_app));
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == C.SELECT_SUCCESS) {
                String path = data.getStringExtra("path") + File.separator;
                Log.i(TAG, "onActivityResult: " + path);
                PreferencesHelper.setPath(path);
                Snackbar.make(getRootView(), R.string.path_success, Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
