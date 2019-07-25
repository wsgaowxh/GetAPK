package com.tgc.getapk.common.utils;

import android.os.Environment;

import com.tgc.getapk.common.C;

import java.io.File;

/**
 * Created by tgc on 17-12-26.
 */

public class PreferencesHelper {
    private static String defaultPath = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + File.separator + "MyBackAPK" + File.separator;

    public static void setPath(String path) {
        SPUtils.putString(C.PATH, path);
    }

    public static String getPath() {
        return SPUtils.getString(C.PATH, defaultPath);
    }

    public static boolean getNameMode() {
        return SPUtils.getBoolean(C.APP_NAME, true);
    }

    public static void setNameMode(boolean value) {
        SPUtils.putBoolean(C.APP_NAME, value);
    }

    public static boolean getCodeMode() {
        return SPUtils.getBoolean(C.APP_CODE, true);
    }

    public static void setCodeMode(boolean value) {
        SPUtils.putBoolean(C.APP_CODE, value);
    }

    public static boolean getPkgMode() {
        return SPUtils.getBoolean(C.APP_PKG, false);
    }

    public static void setPkgMode(boolean value) {
        SPUtils.putBoolean(C.APP_PKG, value);
    }

    public static boolean getInCodeMode() {
        return SPUtils.getBoolean(C.APP_IN_CODE, false);
    }

    public static void setInCodeMode(boolean value) {
        SPUtils.putBoolean(C.APP_IN_CODE, value);
    }

    public static void setRule(boolean value) {
        SPUtils.putBoolean(C.RULE, value);
    }

    public static boolean getRule() {
        return SPUtils.getBoolean(C.RULE, false);
    }
}
