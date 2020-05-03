package com.own.midterm.base;

public abstract class BaseActivityPresenter <V extends BaseActivity, M extends BaseModel> {
    public V mView;

    public M mModel;

    public BaseActivityPresenter(){
        this.mModel = getModelInstance();
    }

    public abstract M getModelInstance();

    public void bindView(V mView){
        this.mView = mView;
    }

    void unBindView(){
        this.mView = null;
    }

}