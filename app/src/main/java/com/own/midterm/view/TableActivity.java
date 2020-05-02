package com.own.midterm.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.own.midterm.R;
import com.own.midterm.base.BaseActivity;

import com.own.midterm.base.BasePresenter;
import com.own.midterm.presenter.FragmentAdapter;

public class TableActivity extends BaseActivity {

    private int[] selectTabRes = new int[]{R.drawable.flame,R.drawable.account, R.drawable.library};

    private ViewPager viewPager;

    private TabLayout tabLayout;

    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(actionBar!=null)
            actionBar.hide();
    }

    @Override
    public BasePresenter getPresenterInstance() {
        return null;
    }

    @Override
    public void initView() {
        actionBar = getSupportActionBar();
        viewPager = findViewById(R.id.viewpager_content);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0;i<3;i++){
            tabLayout.getTabAt(i).setIcon(selectTabRes[i]);
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public int getContentViewID() {
        return R.layout.table_layout;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {

    }
}
