package com.tgc.getapk.mvp.view;

import android.content.pm.ResolveInfo;

import com.tgc.getapk.base.BaseView;

import java.util.ArrayList;

/**
 * Created by TGC on 2017/10/18.
 */

public interface HomeView extends BaseView {
    void load(ArrayList<ResolveInfo> dataList);
}
