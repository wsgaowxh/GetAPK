package com.tgc.getapk.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TGC on 2017/4/16.
 */

public class CopyUtil {

    public static List<Boolean> backupApp(String packageName, Activity mActivity) {
        List<Boolean> resultList = new ArrayList<>();
        //存放位置
        String newFile = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator + "MyBackAPK" + File.separator;
        String oldFile;
        FileInputStream fis;
        FileOutputStream fos;
        try {
            //原始位置
            oldFile = mActivity.getPackageManager().getApplicationInfo(
                    packageName, 0).sourceDir;

            File in = new File(oldFile);
            File file=new File(newFile);
            if (!file.exists()){
                file.mkdirs();
            }
            File out = new File(newFile + packageName + ".apk");
            boolean isCreat=out.exists();
            //情况1：isCreat=false，isOK有值
            //情况2：isCreat=true，isOK没有值
            if (!isCreat) {
                resultList.add(isCreat);
                boolean isOK = out.createNewFile();
                if (isOK){
                    resultList.add(isOK);
                }else {
                    resultList.add(isOK);
                }
            } else {
                resultList.add(isCreat);
            }

            fis = new FileInputStream(in);
            fos = new FileOutputStream(out);

            int count;
            //文件太大的话，我觉得需要修改
            byte[] buffer = new byte[1024 * 1024];
            while ((count = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fis.close();
            fos.flush();
            fos.close();


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(mActivity,"error 001",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mActivity,"error 002", Toast.LENGTH_SHORT).show();
        }
        return resultList;
    }

}
