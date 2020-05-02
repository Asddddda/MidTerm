package com.own.midterm.base;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements View.OnClickListener {

    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewID());

        mPresenter = getPresenterInstance();
        initView();
        initListener();
    }


    public abstract P getPresenterInstance();

    public abstract void initView();

    public abstract void initListener();

    public abstract int getContentViewID();

    public abstract void destroy();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy();
    }
}