package com.tgc.getapk.common.utils;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.tgc.getapk.base.App;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by tgc on 17-12-27.
 */

public class Utils {
    public static String getVersion() {
        PackageManager manager = App.getContext().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(App.getContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info == null) {
            return null;
        }
        return info.versionName;
    }

    public static void scanFile(String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        App.getContext().sendBroadcast(scanIntent);
    }

    public static List<File> getFileListByPath(String path) {
        File directory = new File(path);
        File[] files = directory.listFiles();

        if (files == null) {
            return new ArrayList<>();
        }

        List<File> result = Arrays.asList(files);
        Collections.sort(result, new FileComparator());
        return result;
    }

    public static void saveNameMode(int which, boolean value) {
        // 匹配到which即进行对应存储
        switch (which) {
            case 0: // Name
                PreferencesHelper.setNameMode(value);
                break;
            case 1: // Code
                PreferencesHelper.setCodeMode(value);
                break;
            case 2: // Pkg
                PreferencesHelper.setPkgMode(value);
                break;
            case 3: // In Code
                PreferencesHelper.setInCodeMode(value);
                break;
            default:
                break;
        }
    }

    public static boolean getNameMode(int which) {
        // 匹配到which即进行对应返回
        switch (which) {
            case 0: // Name
                return PreferencesHelper.getNameMode();
            case 1: // Code
                return PreferencesHelper.getCodeMode();
            case 2: // Pkg
                return PreferencesHelper.getPkgMode();
            case 3: // In Code
                return PreferencesHelper.getInCodeMode();
            default:
                return false;
        }
    }

    public static ArrayList<ResolveInfo> getSearchAppList(PackageManager pm, String keyWord) {
        //获取应用列表
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ArrayList<ResolveInfo> appList = (ArrayList<ResolveInfo>) pm.queryIntentActivities(intent, 0);
        Collections.sort(appList, new ResolveInfo.DisplayNameComparator(pm));

        ArrayList<ResolveInfo> list = new ArrayList<>();
        for (int i = 0; i < appList.size(); i++) {
            if (appList.get(i).loadLabel(pm).toString().contains(keyWord)) {
                list.add(appList.get(i));
            }
        }
        return list;
    }
}
