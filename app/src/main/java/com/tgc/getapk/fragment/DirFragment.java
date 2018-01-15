package com.tgc.getapk.fragment;


import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class DirFragment extends BaseFragment {

    public static final String TAG = "DirFragment";
    private Toolbar toolbar;
    private RecyclerView dirRecycler;
    private Button okBtn;

    private static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private TextView pathTv;
    private Button upBtn;
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
        dirRecycler.setAdapter(dirAdapter);
        dirRecycler.setLayoutManager(layoutManager);
        dirAdapter.setOnItemClickListener(new DirAdapter.OnItemClickListener() {
            @Override
            public void click(int position) {
                if (fileList.get(position).isDirectory()) {
                    currentPath = fileList.get(position).getAbsolutePath();
                    fileList = Utils.getFileListByPath(currentPath);
                    dirAdapter.updateFilePath(fileList);
                    if (dirAdapter.getItemCount() > 0) {
                        dirRecycler.scrollToPosition(0);
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
                    currentPath = file.getParent();
                    fileList = Utils.getFileListByPath(currentPath);
                    dirAdapter.updateFilePath(fileList);
                    if (dirAdapter.getItemCount() > 0) {
                        dirRecycler.scrollToPosition(0);
                    }
                    pathTv.setText(currentPath);
                }
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
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
        toolbar = (Toolbar) getRootView().findViewById(R.id.toolbar);
        dirRecycler = (RecyclerView) getRootView().findViewById(R.id.dir_lv);
        okBtn = (Button) getRootView().findViewById(R.id.select_btn);
        pathTv = (TextView) getRootView().findViewById(R.id.path_tv);
        upBtn = (Button) getRootView().findViewById(R.id.up_btn);
        setupToolbar(R.string.select_dir, toolbar, 0);
        toolbar.getMenu().clear();
        pathTv.setText(ROOT_PATH);
    }

    @Override
    protected void initPresenter() {

    }
}
