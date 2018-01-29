package com.tgc.getapk.common.asynctask;

import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.tgc.getapk.base.App;
import com.tgc.getapk.common.C;
import com.tgc.getapk.common.utils.InitAPP;

import java.util.ArrayList;

/**
 * Created by tgc on 2018/1/8.
 */

public class LoadAsyncTask extends AsyncTask<Void, Void, ArrayList<ResolveInfo>> {
    private Handler handler;
    private int type;

    public LoadAsyncTask(Handler handler, int type) {
        this.handler = handler;
        this.type = type;
    }

    @Override
    protected ArrayList<ResolveInfo> doInBackground(Void... voids) {
        switch (type) {
            case C.NON_SYS_APP:
                return InitAPP.initNonSysApp(App.getContext().getPackageManager());
            case C.SYS_APP:
                return InitAPP.initSysApp(App.getContext().getPackageManager());
            default:
                return InitAPP.initSysApp(App.getContext().getPackageManager());
        }
    }

    @Override
    protected void onPostExecute(ArrayList<ResolveInfo> resolveInfos) {
        super.onPostExecute(resolveInfos);
        postBundle(resolveInfos);
    }

    private void postBundle(ArrayList<ResolveInfo> resolveInfos) {
        switch (type) {
            case C.NON_SYS_APP:
                Message messageNonSys = Message.obtain();
                messageNonSys.what = C.NON_SYS_MSG;
                Bundle bundleNonSys = new Bundle();
                bundleNonSys.putParcelableArrayList(C.NON_SYS_BUNDLE, resolveInfos);
                messageNonSys.setData(bundleNonSys);
                handler.sendMessage(messageNonSys);
                break;
            case C.SYS_APP:
                Message messageSys = Message.obtain();
                messageSys.what = C.SYS_MSG;
                Bundle bundleSys = new Bundle();
                bundleSys.putParcelableArrayList(C.SYS_BUNDLE, resolveInfos);
                messageSys.setData(bundleSys);
                handler.sendMessage(messageSys);
                break;
            default:
                break;
        }
    }
}
