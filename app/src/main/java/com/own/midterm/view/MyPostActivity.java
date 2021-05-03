package com.own.midterm.view;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.own.midterm.R;
import com.own.midterm.base.BaseActivity;
import com.own.midterm.model.Lost;
import com.own.midterm.model.UpdateMyPostEvent;
import com.own.midterm.presenter.DelLostAdaptor;
import com.own.midterm.util.BusUtil.BusUtil;
import com.own.midterm.util.BusUtil.EventUtil;
import com.own.midterm.util.BusUtil.ThreadModel;
import com.own.midterm.util.MyJSON.MyJSON;
import java.util.List;


import static com.own.midterm.util.Other.makeStatusBarTransparent;

public class MyPostActivity extends BaseActivity{

    private  String uid="123456";

    private RecyclerView recyclerView;


    @Override
    public void initView() {
        makeStatusBarTransparent(this);
        recyclerView= findViewById(R.id.mypost_recy);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusUtil.getDefault().register(this);
        MyJSON myJSON = new MyJSON(this);
        SharedPreferences sp = getSharedPreferences("account",MODE_PRIVATE);
        uid = sp.getString("uid","123456");
        myJSON.request("Selflostproperty.php"+"?uid=",uid);
    }

    @EventUtil(threadModel = ThreadModel.MAIN)
    public void show(UpdateMyPostEvent event) {
        List<Lost> list = event.getLostList();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(new DelLostAdaptor(list));
    }


    @Override
    public void initListener() {

    }

    @Override
    public int getContentViewID() {
        return R.layout.mypost_layout;
    }

    @Override
    public void destroy() {
        BusUtil.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {

    }
}