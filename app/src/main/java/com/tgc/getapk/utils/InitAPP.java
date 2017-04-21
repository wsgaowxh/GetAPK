package com.tgc.getapk.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by TGC on 2017/4/16.
 */

public class InitAPP {

    public static ArrayList<ResolveInfo> initApp(final Context context, final PackageManager pm) {
        //获取应用列表
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ArrayList<ResolveInfo> appList = (ArrayList<ResolveInfo>) pm.queryIntentActivities(intent, 0);

        Collections.sort(appList, new ResolveInfo.DisplayNameComparator(pm));

        return appList;
    }
}
