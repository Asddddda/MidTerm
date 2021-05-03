package com.own.midterm.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getContentViewID(),null);
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListener();
    }


    public abstract void initView();

    public abstract void initListener();

    public abstract int getContentViewID();

//    public Activity setRoot(Activity activity){
//        return activity;
//    }

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
