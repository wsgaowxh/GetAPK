package com.tgc.getapk.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * Created by TGC on 2017/10/18.
 */

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance;
    }

    public static Resources getAppResources() {
        return instance.getResources();
    }
}
