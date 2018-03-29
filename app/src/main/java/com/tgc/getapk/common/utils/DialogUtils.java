package com.tgc.getapk.common.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import com.tgc.getapk.R;
import com.tgc.getapk.base.App;

/**
 * Created by TGC on 2017/4/16.
 */

public class DialogUtils {

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

    public static ProgressDialog alertHasReturn(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        return progressDialog;
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
        String[] items = new String[]{context.getString(R.string.mode_name),
                context.getString(R.string.mode_version_code),
                context.getString(R.string.mode_pkg_name),
                context.getString(R.string.mode_in_code)};
        final boolean[] itemsStatus = new boolean[items.length];
        for (int i = 0; i < items.length; i++) {
            itemsStatus[i] = Utils.getNameMode(i);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.select_mode);
        builder.setMultiChoiceItems(items, itemsStatus, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                itemsStatus[which] = isChecked;
            }
        });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int j = 0;
                for (int i = 0; i < itemsStatus.length; i++) {
                    if (itemsStatus[i]) {
                        Utils.saveNameMode(i, true);
                    } else {
                        Utils.saveNameMode(i, false);
                        j++;
                    }
                }
                if (j >= itemsStatus.length) {
                    Utils.saveNameMode(0,true);
                    Utils.saveNameMode(1, true);
                }
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
