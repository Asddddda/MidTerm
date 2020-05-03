package com.own.midterm.presenter;

import android.app.Activity;
import android.content.Context;

import com.own.midterm.base.BaseModel;
import com.own.midterm.base.BasePresenter;
import com.own.midterm.contract.MainContract;
import com.own.midterm.model.MainModel;
import com.own.midterm.view.MainFragment;

public class MainPresenter extends BasePresenter<MainFragment,MainModel> implements MainContract.P {

    @Override
    public MainModel getModelInstance() {
        return new MainModel(this);
    }

    @Override
    public void askM(Activity activity) {
        mModel.getInfo(activity);
    }

    @Override
    public void informV(String info) {
        mView.show(info);
    }
}
