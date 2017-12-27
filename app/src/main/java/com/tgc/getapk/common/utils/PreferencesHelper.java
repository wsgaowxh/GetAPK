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
}
