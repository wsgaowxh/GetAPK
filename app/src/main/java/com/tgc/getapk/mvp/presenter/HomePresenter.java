package com.tgc.getapk.mvp.presenter;

import android.content.Context;
import android.content.pm.ResolveInfo;

import com.tgc.getapk.base.App;
import com.tgc.getapk.base.BasePresenter;
import com.tgc.getapk.base.BaseView;
import com.tgc.getapk.common.asynctask.CopyAsyncTask;
import com.tgc.getapk.common.utils.InitAPP;
import com.tgc.getapk.mvp.view.HomeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TGC on 2017/10/18.
 */

public class HomePresenter extends BasePresenter {

    HomeView iView;

    public void load() {
        ArrayList<ResolveInfo> dataList = InitAPP
                .initApp(App.getContext(), App.getContext().getPackageManager());
        iView.load(dataList);
    }

    public void startCopy(int appID, List<ResolveInfo> dataList, Context context) {
        ResolveInfo app = dataList.get(appID);
        final String packName = app.activityInfo.packageName;
        CopyAsyncTask copyAsyncTask = new CopyAsyncTask(context, packName);
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
