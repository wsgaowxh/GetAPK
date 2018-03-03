package com.tgc.getapk.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.tgc.getapk.base.App;

/**
 * Created by tgc on 17-12-26.
 */

public class SPUtils {
    private static final String currentTable = "GetAPK";

    public static void putString(String key, String value) {
        SharedPreferences preferences = App.getInstance()
                .getSharedPreferences(currentTable, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).apply();
    }

    public static String getString(String key, String defaultValue) {
        SharedPreferences preferences = App.getInstance()
                .getSharedPreferences(currentTable, Context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }

    public static void putInt(String key, int value) {
        SharedPreferences preferences = App.getInstance()
                .getSharedPreferences(currentTable, Context.MODE_PRIVATE);
        preferences.edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defaultValue) {
        SharedPreferences preferences = App.getInstance()
                .getSharedPreferences(currentTable, Context.MODE_PRIVATE);
        return preferences.getInt(key, defaultValue);
    }
}
