package com.tgc.getapk.common.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.tgc.getapk.base.App;

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
}
