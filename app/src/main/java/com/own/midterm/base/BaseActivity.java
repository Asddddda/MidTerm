package com.own.midterm.base;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 大概写的一个MVP，之前尝试MVP之间持有接口解耦时出了神奇bug，尝试用抽象类实现，后面才发现坑的。。。还得接口
 * @param <P>
 */
public abstract class BaseActivity<P extends BaseActivityPresenter> extends AppCompatActivity implements View.OnClickListener {

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