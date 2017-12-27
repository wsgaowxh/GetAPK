package com.tgc.getapk.common.utils;

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
}
