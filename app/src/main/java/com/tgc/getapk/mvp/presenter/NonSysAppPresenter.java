package com.tgc.getapk.mvp.presenter;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.tgc.getapk.base.App;
import com.tgc.getapk.base.BasePresenter;
import com.tgc.getapk.base.BaseView;
import com.tgc.getapk.common.C;
import com.tgc.getapk.common.asynctask.CopyAsyncTask;
import com.tgc.getapk.common.utils.InitAPP;
import com.tgc.getapk.mvp.view.NonSysAppView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tgc on 2018/1/29.
 */

public class NonSysAppPresenter extends BasePresenter {
    private NonSysAppView iView;
    private Handler handler;

    static class NonSysHandler extends Handler {
        WeakReference<NonSysAppView> weakReference;
        public NonSysHandler(NonSysAppView view) {
            weakReference = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == C.NON_SYS_MSG) {
                Bundle data = msg.getData();
                ArrayList<ResolveInfo> dataList = data.getParcelableArrayList(C.NON_SYS_BUNDLE);
                if (dataList != null) {
                    NonSysAppView nonSysAppView = weakReference.get();
                    nonSysAppView.load(dataList);
                }
            }
        }
    }

    public void load() {
//        final Handler handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                if (msg.what == C.NON_SYS_MSG) {
//                    Bundle data = msg.getData();
//                    ArrayList<ResolveInfo> dataList = data.getParcelableArrayList(C.NON_SYS_BUNDLE);
//                    if (dataList != null) {
//                        iView.load(dataList);
//                    }
//                }
//            }
//        };
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

    public void startCopy(int id, List<ResolveInfo> dataList, Context context) {
        List<Integer> appID = new ArrayList();
        appID.add(id);
        CopyAsyncTask copyAsyncTask = new CopyAsyncTask(context, appID, dataList);
        copyAsyncTask.execute();
    }

    @Override
    public void attachView(BaseView view) {
        this.iView = (NonSysAppView) view;
        handler = new NonSysHandler(iView);
    }

    @Override
    public void detachView() {

    }
}
