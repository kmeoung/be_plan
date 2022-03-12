package com.nguyenhoanglam.imagepicker.ui.common;

import com.nguyenhoanglam.imagepicker.ui.common.MvpView;

/* loaded from: classes.dex */
public class BasePresenter<T extends MvpView> {
    private T view;

    public void attachView(T t) {
        this.view = t;
    }

    public T getView() {
        return this.view;
    }

    public void detachView() {
        this.view = null;
    }

    public boolean isViewAttached() {
        return this.view != null;
    }
}
