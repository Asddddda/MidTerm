package com.own.midterm.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.own.midterm.R;
import com.own.midterm.base.BaseActivity;
import com.own.midterm.base.BaseActivityPresenter;

import java.io.File;
import java.util.logging.Handler;

import static com.own.midterm.util.Other.makeStatusBarTransparent;

public class PlayActivity extends BaseActivity {

    private ImageButton backButton;

    private ImageButton playButton;

    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar.hide();
    }

    @Override
    public BaseActivityPresenter getPresenterInstance() {
        return null;
    }

    @Override
    public void initView() {
        actionBar = getSupportActionBar();
        makeStatusBarTransparent(this);
        backButton = findViewById(R.id.back);
        playButton = findViewById(R.id.play_button);
    }

    @Override
    public void initListener() {
        playButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    @Override
    public int getContentViewID() {
        return R.layout.play_layout;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
