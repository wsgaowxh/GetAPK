package com.tgc.getapk.mvp.presenter;

import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.tgc.getapk.base.App;
import com.tgc.getapk.base.BasePresenter;
import com.tgc.getapk.base.BaseView;
import com.tgc.getapk.common.C;
import com.tgc.getapk.common.utils.InitAPP;
import com.tgc.getapk.mvp.view.NonSysAppView;

import java.util.ArrayList;

/**
 * Created by tgc on 2018/1/29.
 */

public class NonSysAppPresenter extends BasePresenter {
    private NonSysAppView iView;

    public void load() {
        final Handler handler = new Handler() {
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
        new Thread() {
            @Override
            public void run() {
                super.run();
                ArrayList<ResolveInfo> resolveInfos = InitAPP
                        .initNonSysApp(App.getContext().getPackageManager());
                postBundle(resolveInfos, handler);
            }
        }.start();
    }

    private void postBundle(ArrayList<ResolveInfo> resolveInfos, Handler handler) {
        Message messageNonSys = Message.obtain();
        messageNonSys.what = C.NON_SYS_MSG;
        Bundle bundleNonSys = new Bundle();
        bundleNonSys.putParcelableArrayList(C.NON_SYS_BUNDLE, resolveInfos);
        messageNonSys.setData(bundleNonSys);
        handler.sendMessage(messageNonSys);
    }

    @Override
    public void attachView(BaseView view) {
        this.iView = (NonSysAppView) view;
    }

    @Override
    public void detachView() {

    }
}
