package com.tgc.getapk.common.utils;

import android.content.pm.ResolveInfo;

import com.tgc.getapk.base.App;

import java.util.List;

/**
 * Created by TGC on 2017/12/9.
 */

public class GetAppInfoUtil {

    public static String getPkgName(int appID, List<ResolveInfo> dataList) {
        ResolveInfo app = dataList.get(appID);
        return app.activityInfo.packageName;
    }

    public static String getAppName(int appID, List<ResolveInfo> dataList) {
        ResolveInfo resolveInfo = dataList.get(appID);
        return resolveInfo.loadLabel(App.getContext().getPackageManager()).toString();
    }
}
