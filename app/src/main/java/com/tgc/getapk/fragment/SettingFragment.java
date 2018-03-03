package com.tgc.getapk.fragment;


import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tgc.getapk.R;
import com.tgc.getapk.base.BaseFragment;
import com.tgc.getapk.common.utils.DialogUtils;
import com.tgc.getapk.common.utils.Utils;
import com.tgc.getapk.mvp.view.SettingsView;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment implements SettingsView,
        AdapterView.OnItemClickListener {

    public static final String TAG = "SettingFragment";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv_setting)
    ListView lvSetting;

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
                addFragment(new DirFragment(), DirFragment.TAG, true);
                break;
            case 1:
                DialogUtils.selectFileNameMode(getContext());
                break;
            case 2:
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

}
