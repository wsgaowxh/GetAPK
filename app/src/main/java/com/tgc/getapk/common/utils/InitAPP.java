package com.tgc.getapk.common.utils;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by TGC on 2017/4/16.
 */

public class InitAPP {

    public static ArrayList<ResolveInfo> initNonSysApp(PackageManager pm) {
        //获取应用列表
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ArrayList<ResolveInfo> appList = (ArrayList<ResolveInfo>) pm.queryIntentActivities(intent, 0);
        Collections.sort(appList, new ResolveInfo.DisplayNameComparator(pm));

        ArrayList<ResolveInfo> list = new ArrayList<>();
        for (int i = 0; i < appList.size(); i++) {
            try {
                PackageInfo packageInfo = pm.getPackageInfo(appList.get(i).activityInfo.packageName, 0);
                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                    // 第三方应用
                    list.add(appList.get(i));
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static ArrayList<ResolveInfo> initSysApp(PackageManager pm) {
        //获取应用列表
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ArrayList<ResolveInfo> appList = (ArrayList<ResolveInfo>) pm.queryIntentActivities(intent, 0);
        Collections.sort(appList, new ResolveInfo.DisplayNameComparator(pm));

        ArrayList<ResolveInfo> list = new ArrayList<>();
        for (int i = 0; i < appList.size(); i++) {
            try {
                PackageInfo packageInfo = pm.getPackageInfo(appList.get(i).activityInfo.packageName, 0);
                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
                    // 第三方应用
                    list.add(appList.get(i));
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
