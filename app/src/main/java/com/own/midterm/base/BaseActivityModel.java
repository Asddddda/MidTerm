package com.own.midterm.base;

public class BaseActivityModel<P extends BaseActivityPresenter> {

    public P mPresenter;

    public BaseActivityModel (P mPresenter){
        this.mPresenter = mPresenter;
    }
}
