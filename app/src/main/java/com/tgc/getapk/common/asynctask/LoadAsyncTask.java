package com.tgc.getapk.common.asynctask;

import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.tgc.getapk.base.App;
import com.tgc.getapk.common.utils.InitAPP;

import java.util.ArrayList;

/**
 * Created by tgc on 2018/1/8.
 */

public class LoadAsyncTask extends AsyncTask<Void, Void, ArrayList<ResolveInfo>> {
    Handler handler;

    public LoadAsyncTask(Handler handler) {
        this.handler = handler;
    }

    @Override
    protected ArrayList<ResolveInfo> doInBackground(Void... voids) {
        return InitAPP.initApp(App.getContext(), App.getContext().getPackageManager());
    }

    @Override
    protected void onPostExecute(ArrayList<ResolveInfo> resolveInfos) {
        super.onPostExecute(resolveInfos);
        Message message = Message.obtain();
        message.what = 1;
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("resolveInfos", resolveInfos);
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
