package com.own.midterm.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.own.midterm.base.BasePresenter;
import com.own.midterm.contract.MainContract;
import com.own.midterm.contract.ShowContract;
import com.own.midterm.model.AccountModel;
import com.own.midterm.view.AccountFragment;


public class AccountPresenter extends BasePresenter<AccountFragment, AccountModel>implements ShowContract.P{

    @Override
    public AccountModel getModelInstance() {
        return new AccountModel(this);
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
