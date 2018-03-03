package com.tgc.getapk.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import com.tgc.getapk.R;
import com.tgc.getapk.base.App;
import com.tgc.getapk.common.C;

/**
 * Created by TGC on 2017/4/16.
 */

public class DialogUtils {
    private static String[] fileNameModeItems = new String[]{App.getContext().getString(R.string.mode_1),
            App.getContext().getString(R.string.mode_2),
            App.getContext().getString(R.string.mode_3)};

    public static void alert(Context context, @StringRes int title, @StringRes int msg) {
        String s = App.getAppResources().getString(msg);
        alert(context, title, s);
    }

    public static void alert(Context context, @StringRes int title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void alertFinishNoTitle(Context context, String msg, final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void selectFileNameMode(Context context) {
        int fileNameMode = PreferencesHelper.getFileNameMode();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.select_mode);
        builder.setSingleChoiceItems(fileNameModeItems, fileNameMode - 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        PreferencesHelper.setFileNameMode(C.NAME_CODE_NAME);
                        break;
                    case 1:
                        PreferencesHelper.setFileNameMode(C.NAME_CODE_PKG_IN_NAME);
                        break;
                    case 2:
                        PreferencesHelper.setFileNameMode(C.PKG_NAME);
                        break;
                    default:
                        PreferencesHelper.setFileNameMode(C.PKG_NAME);
                        break;
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
