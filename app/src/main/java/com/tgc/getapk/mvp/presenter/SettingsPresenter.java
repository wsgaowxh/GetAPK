package com.tgc.getapk.mvp.presenter;

import com.tgc.getapk.base.BasePresenter;
import com.tgc.getapk.base.BaseView;
import com.tgc.getapk.mvp.view.SettingsView;

/**
 * Created by tgc on 17-12-26.
 */

public class SettingsPresenter extends BasePresenter {
    private SettingsView iView;

    @Override
    public void attachView(BaseView view) {
        this.iView = (SettingsView) view;
    }

    @Override
    public void detachView() {

    }
}
