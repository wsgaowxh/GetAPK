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
import com.tgc.getapk.common.utils.Utils;
import com.tgc.getapk.mvp.view.HomeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TGC on 2017/10/18.
 */

public class HomePresenter extends BasePresenter {

    private HomeView iView;

    public void startCopyWithList(List<Integer> appID, List<ResolveInfo> dataList, Context context) {
        CopyAsyncTask copyAsyncTask = new CopyAsyncTask(context, appID, dataList);
        copyAsyncTask.execute();
    }

    public void getSearchAppList(final String keyWord) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == C.SEARCH_MSG) {
                    Bundle data = msg.getData();
                    ArrayList<ResolveInfo> dataList = data.getParcelableArrayList(C.SEARCH_BUNDLE);
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
                ArrayList<ResolveInfo> searchAppList = Utils
                        .getSearchAppList(App.getInstance().getPackageManager(), keyWord);
                postBundle(searchAppList, handler);
            }
        }.start();
    }

    private void postBundle(ArrayList<ResolveInfo> resolveInfos, Handler handler) {
        Message message = Message.obtain();
        message.what = C.SEARCH_MSG;
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(C.SEARCH_BUNDLE, resolveInfos);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    @Override
    public void attachView(BaseView view) {
        this.iView = (HomeView) view;
    }

    @Override
    public void detachView() {

    }
}
