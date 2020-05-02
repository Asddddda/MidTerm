package com.own.midterm.base;

public abstract class BasePresenter<V extends BaseFragment, M extends BaseModel> {
    public V mView;

    public M mModel;

    public BasePresenter(){
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