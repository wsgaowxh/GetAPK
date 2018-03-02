package com.tgc.getapk.mvp.presenter;

import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.tgc.getapk.base.BasePresenter;
import com.tgc.getapk.base.BaseView;
import com.tgc.getapk.common.C;
import com.tgc.getapk.common.asynctask.LoadAsyncTask;
import com.tgc.getapk.mvp.view.SysAppView;

import java.util.ArrayList;

/**
 * Created by tgc on 2018/1/29.
 */

public class SysAppPresenter extends BasePresenter {
    SysAppView iView;

    public void load() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == C.SYS_MSG) {
                    Bundle data = msg.getData();
                    ArrayList<ResolveInfo> dataList = data.getParcelableArrayList(C.SYS_BUNDLE);
                    if (dataList != null) {
                        iView.load(dataList);
                    }
                }
            }
        };
        LoadAsyncTask loadAsyncTask = new LoadAsyncTask(handler, C.SYS_APP);
        loadAsyncTask.execute();
    }

    @Override
    public void attachView(BaseView view) {
        this.iView = (SysAppView) view;
    }

    @Override
    public void detachView() {

    }
}