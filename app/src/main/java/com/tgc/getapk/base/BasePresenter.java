package com.tgc.getapk.base;

/**
 * Created by ivan on 17-4-11.
 */

public abstract class BasePresenter {


    public abstract void attachView(BaseView view);

    public abstract void detachView();
}
