package com.own.midterm.base;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(getContentViewID());
        initView();
        initListener();
    }

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