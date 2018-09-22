package com.tgc.getapk.common.comparator;

import android.content.pm.ResolveInfo;

import com.tgc.getapk.base.App;
import com.tgc.getapk.common.utils.PinYinUtils;

import java.util.Comparator;

public class ResolveInfoComparator implements Comparator<ResolveInfo> {
    @Override
    public int compare(ResolveInfo t0, ResolveInfo t1) {
        String appName0 = PinYinUtils.converterToFirstSpell(t0.loadLabel(App.getContext().getPackageManager()).toString());
        String appName1 = PinYinUtils.converterToFirstSpell(t1.loadLabel(App.getContext().getPackageManager()).toString());
        return appName0.compareToIgnoreCase(appName1);
    }
}
