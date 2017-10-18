package com.tgc.getapk.mvp.view;

import android.content.pm.ResolveInfo;

import com.tgc.getapk.base.BaseView;

import java.util.List;

/**
 * Created by TGC on 2017/10/18.
 */

public interface HomeView extends BaseView {
    void load(List<ResolveInfo> dataList);
}
