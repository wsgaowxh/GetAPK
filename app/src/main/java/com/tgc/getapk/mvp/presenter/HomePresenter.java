package com.tgc.getapk.mvp.presenter;

import android.content.Context;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.tgc.getapk.base.BasePresenter;
import com.tgc.getapk.base.BaseView;
import com.tgc.getapk.common.asynctask.CopyAsyncTask;
import com.tgc.getapk.common.asynctask.LoadAsyncTask;
import com.tgc.getapk.mvp.view.HomeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TGC on 2017/10/18.
 */

public class HomePresenter extends BasePresenter {

    HomeView iView;

    public void load() {
//        ArrayList<ResolveInfo> dataList = InitAPP
//                .initApp(App.getContext(), App.getContext().getPackageManager());
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    Bundle data = msg.getData();
                    ArrayList<ResolveInfo> dataList = data.getParcelableArrayList("resolveInfos");
                    if (dataList != null) {
                        iView.load(dataList);
                    }
                }
            }
        };
        LoadAsyncTask loadAsyncTask = new LoadAsyncTask(handler);
        loadAsyncTask.execute();
    }

//    public void startCopy(int appID, List<ResolveInfo> dataList, Context context) {
//        ResolveInfo app = dataList.get(appID);
//        final String packName = app.activityInfo.packageName;
//        CopyAsyncTask copyAsyncTask = new CopyAsyncTask(context, packName);
//        copyAsyncTask.execute();
//    }

    public void startCopyWithList(List<Integer> appID, List<ResolveInfo> dataList, Context context) {
        CopyAsyncTask copyAsyncTask = new CopyAsyncTask(context, appID, dataList);
        copyAsyncTask.execute();
    }

    @Override
    public void attachView(BaseView view) {
        this.iView = (HomeView) view;
    }

    @Override
    public void detachView() {

    }
}
