package com.tgc.getapk.common.asynctask;

import android.content.pm.ResolveInfo;
import android.os.AsyncTask;

import com.tgc.getapk.base.App;
import com.tgc.getapk.common.utils.InitAPP;

import java.util.ArrayList;

/**
 * Created by tgc on 2018/1/8.
 */

public class LoadAsyncTask extends AsyncTask<Void, Void, ArrayList<ResolveInfo>> {

    @Override
    protected ArrayList<ResolveInfo> doInBackground(Void... voids) {
        return InitAPP.initApp(App.getContext(), App.getContext().getPackageManager());
    }
}
