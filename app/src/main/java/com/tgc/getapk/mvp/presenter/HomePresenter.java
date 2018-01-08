package com.tgc.getapk.mvp.presenter;

import android.content.Context;
import android.content.pm.ResolveInfo;

import com.tgc.getapk.base.BasePresenter;
import com.tgc.getapk.base.BaseView;
import com.tgc.getapk.common.asynctask.CopyAsyncTask;
import com.tgc.getapk.common.asynctask.LoadAsyncTask;
import com.tgc.getapk.mvp.view.HomeView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by TGC on 2017/10/18.
 */

public class HomePresenter extends BasePresenter {

    HomeView iView;

    public void load() {
//        ArrayList<ResolveInfo> dataList = InitAPP
//                .initApp(App.getContext(), App.getContext().getPackageManager());
        LoadAsyncTask loadAsyncTask = new LoadAsyncTask();
        ArrayList<ResolveInfo> dataList = null;
        try {
            dataList = loadAsyncTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (dataList != null) {
            iView.load(dataList);
        }
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
