package com.tgc.getapk.common.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.tgc.getapk.base.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by TGC on 2017/4/16.
 */

public class CopyUtil {

    public static int backupApp(List<Integer> appID, List<ResolveInfo> dataList) {
//        List<Boolean> resultList = new ArrayList<>();
        int resultCode = 0;
        //存放位置
        String newFile = PreferencesHelper.getPath();
        boolean nameMode = PreferencesHelper.getNameMode();
        boolean codeMode = PreferencesHelper.getCodeMode();
        boolean pkgMode = PreferencesHelper.getPkgMode();
        boolean inCodeMode = PreferencesHelper.getInCodeMode();
        String oldFile;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            for (int i = 0; i < appID.size(); i++) {
                String packageName = GetAppInfoUtil.getPkgName(appID.get(i), dataList);
                PackageInfo packageInfo = App.getContext().getPackageManager().getPackageInfo(packageName, 0);
                ApplicationInfo appInfo = App.getContext().getPackageManager().getApplicationInfo(
                        packageName, 0);
                //原始位置
                oldFile = appInfo.sourceDir;

                File in = new File(oldFile);
                File file = new File(newFile);
                if (!file.exists()) {
                    file.mkdirs();
                }

                String filePath = newFile;
                if (nameMode) {
                    filePath = filePath + GetAppInfoUtil.getAppName(appID.get(i), dataList) + "-";
                }
                if (codeMode) {
                    filePath = filePath + packageInfo.versionName + "-";
                }
                if (pkgMode) {
                    filePath = filePath + packageName + "-";
                }
                if (inCodeMode) {
                    filePath = filePath + packageInfo.versionCode + "-";
                }
                //DialogUtils中做了限制，上方四个if至少会执行其中一个
                filePath = filePath.substring(0, filePath.length() - 1) + ".apk";

                File out = new File(filePath);
                boolean isCreat = out.exists();
                //情况1：isCreat=false，isOK有值
                //情况2：isCreat=true，isOK没有值
                if (!isCreat) {
//                resultList.add(isCreat);
                    boolean isOK = out.createNewFile();
                    if (isOK) {
//                    resultList.add(isOK);
                        resultCode = 1;
                        Utils.scanFile(filePath);
                    } else {
//                    resultList.add(isOK);
                        resultCode = 2;
                    }
                } else {
//                resultList.add(isCreat);
                    resultCode = 3;
                }

                fis = new FileInputStream(in);
                fos = new FileOutputStream(out);

                int count;
                //文件太大的话，我觉得需要修改
                byte[] buffer = new byte[1024 * 1024];
                while ((count = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
            }
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.flush();
                fos.close();
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 2;
        } catch (IOException e) {
            e.printStackTrace();
            return 2;
        }
        return resultCode;
    }

}
