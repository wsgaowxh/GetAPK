package com.tgc.getapk.mvp.presenter;

import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.tgc.getapk.base.BasePresenter;
import com.tgc.getapk.base.BaseView;
import com.tgc.getapk.common.C;
import com.tgc.getapk.common.asynctask.LoadAsyncTask;
import com.tgc.getapk.mvp.view.NonSysAppView;

import java.util.ArrayList;

/**
 * Created by tgc on 2018/1/29.
 */

public class NonSysAppPresenter extends BasePresenter {
    NonSysAppView iView;

    public void load(int type) {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == C.NON_SYS_MSG) {
                    Bundle data = msg.getData();
                    ArrayList<ResolveInfo> dataList = data.getParcelableArrayList(C.NON_SYS_BUNDLE);
                    if (dataList != null) {
                        iView.load(dataList);
                    }
                }
            }
        };
        LoadAsyncTask loadAsyncTask = new LoadAsyncTask(handler, type);
        loadAsyncTask.execute();
    }

    @Override
    public void attachView(BaseView view) {
        this.iView = (NonSysAppView) view;
    }

    @Override
    public void detachView() {

    }
}
