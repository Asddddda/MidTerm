package com.own.midterm.model;

import android.content.Context;

import com.own.midterm.base.BaseModel;
import com.own.midterm.base.BasePresenter;
import com.own.midterm.contract.ShowContract;
import com.own.midterm.presenter.AccountPresenter;
import com.own.midterm.util.JSON.JsonObject;
import com.own.midterm.view.AccountFragment;

public class AccountModel extends BaseModel<AccountPresenter> implements ShowContract.M{

    private String request = "/user/detail";

    private String info = "?uid=266653274";

    public AccountModel(AccountPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public void getInfo(Context context) {
        JsonObject jsonObject = new JsonObject(context,this);
        jsonObject.request(request,info);
    }

    @Override
    public void informP() {
        mPresenter.informV();
    }
}