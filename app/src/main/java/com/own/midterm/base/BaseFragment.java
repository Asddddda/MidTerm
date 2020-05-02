package com.own.midterm.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements View.OnClickListener {
    public P mPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewID(),null);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onClick(View v) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroy();
    }
}
