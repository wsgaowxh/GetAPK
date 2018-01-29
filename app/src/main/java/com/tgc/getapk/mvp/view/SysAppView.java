package com.tgc.getapk.mvp.view;

import android.content.pm.ResolveInfo;

import com.tgc.getapk.base.BaseView;

import java.util.List;

/**
 * Created by tgc on 2018/1/29.
 */

public interface SysAppView extends BaseView {
    void load(List<ResolveInfo> dataList);
}
