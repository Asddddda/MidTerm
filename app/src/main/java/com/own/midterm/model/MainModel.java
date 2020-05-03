package com.own.midterm.model;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.own.midterm.R;
import com.own.midterm.base.BaseModel;
import com.own.midterm.contract.ShowContract;
import com.own.midterm.presenter.MainPresenter;
import com.own.midterm.util.Glide.MyGlide;
import com.own.midterm.util.JSON.JsonObject;

public class MainModel extends BaseModel<MainPresenter> implements ShowContract.M {
    String request = "/album/newest";

    String request2 = "/top/playlist";

    String info = "";

    String info2 = "?limit=10";

    public MainModel(MainPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public void getInfo(Context activity) {
        JsonObject jsonObject = new JsonObject(activity,this);
        jsonObject.request(request,info);
        jsonObject.request(request2,info2);
    }

    @Override
    public void informP(String info) {
        mPresenter.informV(info);
    }
}
