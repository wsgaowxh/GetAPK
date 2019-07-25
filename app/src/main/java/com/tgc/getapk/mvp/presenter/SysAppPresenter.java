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
import com.tgc.getapk.mvp.view.SysAppView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tgc on 2018/1/29.
 */

public class SysAppPresenter extends BasePresenter {
    private SysAppView iView;
    private Handler handler;

    static class SysHandler extends Handler {
        WeakReference<SysAppView> weakReference;
        public SysHandler(SysAppView view) {
            weakReference = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == C.SYS_MSG) {
                Bundle data = msg.getData();
                ArrayList<ResolveInfo> dataList = data.getParcelableArrayList(C.SYS_BUNDLE);
                if (dataList != null) {
                    SysAppView sysAppView = weakReference.get();
                    sysAppView.load(dataList);
                }
            }
        }
    }

    public void startCopy(int id, List<ResolveInfo> dataList, Context context) {
        List<Integer> appID = new ArrayList();
        appID.add(id);
        CopyAsyncTask copyAsyncTask = new CopyAsyncTask(context, appID, dataList);
        copyAsyncTask.execute();
    }

    public void load() {
//        final Handler handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                if (msg.what == C.SYS_MSG) {
//                    Bundle data = msg.getData();
//                    ArrayList<ResolveInfo> dataList = data.getParcelableArrayList(C.SYS_BUNDLE);
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
                        .initSysApp(App.getContext().getPackageManager());
                postBundle(resolveInfos, handler);
            }
        }.start();
    }

    private void postBundle(ArrayList<ResolveInfo> resolveInfos, Handler handler) {
        Message messageSys = Message.obtain();
        messageSys.what = C.SYS_MSG;
        Bundle bundleSys = new Bundle();
        bundleSys.putParcelableArrayList(C.SYS_BUNDLE, resolveInfos);
        messageSys.setData(bundleSys);
        handler.sendMessage(messageSys);
    }

    @Override
    public void attachView(BaseView view) {
        this.iView = (SysAppView) view;
        handler = new SysHandler(iView);
    }

    @Override
    public void detachView() {

    }
}
