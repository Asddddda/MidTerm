package com.own.midterm.base;

public class BaseModel<P extends BasePresenter> {

    public P mPresenter;

    public BaseModel(P mPresenter){
        this.mPresenter = mPresenter;
    }

}
