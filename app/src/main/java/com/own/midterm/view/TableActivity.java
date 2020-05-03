package com.own.midterm.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.own.midterm.R;
import com.own.midterm.base.BaseActivity;

import com.own.midterm.base.BaseActivityPresenter;
import com.own.midterm.base.BasePresenter;
import com.own.midterm.presenter.FragmentAdapter;

import static com.own.midterm.util.Other.makeStatusBarTransparent;

public class TableActivity extends BaseActivity {

    private int[] TabRes = new int[]{R.drawable.flame, R.drawable.library,R.drawable.account};

    private int[] selectTabRes = new int[]{R.drawable.flame_sel, R.drawable.library_sel,R.drawable.account_sel};

    private ViewPager viewPager;

    private TabLayout tabLayout;

    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(actionBar!=null)
            actionBar.hide();
        if(ContextCompat.checkSelfPermission(TableActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(TableActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            //这里会调用后面的onRequestPermissionResult
        }

    }

    @Override
    public BaseActivityPresenter getPresenterInstance() {
        return null;
    }

    @Override
    public void initView() {
        makeStatusBarTransparent(this);
        actionBar = getSupportActionBar();
        viewPager = findViewById(R.id.viewpager_content);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0;i<3;i++){
            tabLayout.getTabAt(i).setIcon(TabRes[i]);
        }
    }

    @Override
    public void initListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (int i = 0; i < 3; i++) {
                    if (tab == tabLayout.getTabAt(i)) {
                        tabLayout.getTabAt(i).setIcon(selectTabRes[i]);
                        viewPager.setCurrentItem(i);
                    }
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                for (int i = 0; i < 3; i++) {
                    if (tab == tabLayout.getTabAt(i)) {
                        tabLayout.getTabAt(i).setIcon(TabRes[i]);
                    }
                }

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
