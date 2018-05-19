package com.tgc.getapk.fragment;


import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;

import com.tgc.getapk.R;
import com.tgc.getapk.adapter.DirAdapter;
import com.tgc.getapk.base.App;
import com.tgc.getapk.base.BaseFragment;
import com.tgc.getapk.common.utils.PreferencesHelper;
import com.tgc.getapk.common.utils.Utils;

import java.io.File;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DirFragment extends BaseFragment {

    public static final String TAG = "DirFragment";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.path_tv)
    TextView pathTv;
    @BindView(R.id.dir_lv)
    RecyclerView dirLv;
    @BindView(R.id.up_btn)
    Button upBtn;
    @BindView(R.id.select_btn)
    Button selectBtn;

    private static final String ROOT_PATH = Environment.
            getExternalStorageDirectory().getAbsolutePath() + File.separator;
    String currentPath = ROOT_PATH;
    private List<File> fileList;

    @Override
    protected int getLayout() {
        return R.layout.fragment_dir;
    }

    @Override
    protected void init() {
        fileList = Utils.getFileListByPath(ROOT_PATH);
        final DirAdapter dirAdapter = new DirAdapter(fileList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext(),
                LinearLayoutManager.VERTICAL, false);
        dirLv.setAdapter(dirAdapter);
        dirLv.setLayoutManager(layoutManager);

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(dirLv.getContext(), R.anim.layout_fall_down);
        dirLv.setLayoutAnimation(animation);

        dirAdapter.setOnItemClickListener(new DirAdapter.OnItemClickListener() {
            @Override
            public void click(int position) {
                if (fileList.get(position).isDirectory()) {
                    currentPath = fileList.get(position).getAbsolutePath() + File.separator;
                    fileList = Utils.getFileListByPath(currentPath);
                    dirAdapter.updateFilePath(fileList);
                    dirLv.scheduleLayoutAnimation(); //使RecyclerView每次更新后播放动画
                    if (dirAdapter.getItemCount() > 0) {
                        dirLv.scrollToPosition(0);
                    }
                    pathTv.setText(currentPath);
                }
            }
        });
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPath.length() > ROOT_PATH.length()) {
                    File file = new File(currentPath);
                    currentPath = file.getParent() + File.separator;
                    fileList = Utils.getFileListByPath(currentPath);
                    dirAdapter.updateFilePath(fileList);
                    if (dirAdapter.getItemCount() > 0) {
                        dirLv.scrollToPosition(0);
                    }
                    pathTv.setText(currentPath);
                }
            }
        });
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesHelper.setPath(currentPath);
                Snackbar.make(getRootView(), R.string.path_success, Snackbar.LENGTH_SHORT).show();
                popFragment();
            }
        });
    }

    @Override
    protected void setupView() {
        setupToolbar(R.string.select_dir, toolbar, 0);
        toolbar.getMenu().clear();
        pathTv.setText(ROOT_PATH);
    }

    @Override
    protected void initPresenter() {

    }
}
