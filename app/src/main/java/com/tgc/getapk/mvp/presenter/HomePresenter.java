package com.tgc.getapk.mvp.presenter;

import android.content.Context;
import android.content.pm.ResolveInfo;

import com.tgc.getapk.base.BasePresenter;
import com.tgc.getapk.base.BaseView;
import com.tgc.getapk.common.asynctask.CopyAsyncTask;
import com.tgc.getapk.mvp.view.HomeView;

import java.util.List;

/**
 * Created by TGC on 2017/10/18.
 */

public class HomePresenter extends BasePresenter {

    HomeView iView;

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
