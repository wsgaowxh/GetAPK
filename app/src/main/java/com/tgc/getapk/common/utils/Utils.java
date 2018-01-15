package com.tgc.getapk.common.utils;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
}
