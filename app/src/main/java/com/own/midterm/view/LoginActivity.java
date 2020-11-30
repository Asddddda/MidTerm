package com.own.midterm.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.own.midterm.R;
import com.own.midterm.base.BaseActivity;

import static com.own.midterm.util.Other.makeStatusBarTransparent;

public class LoginActivity extends BaseActivity {
    private EditText userName;

    private EditText password;

    private ImageButton login;

    ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(actionBar!=null)
            actionBar.hide();
    }

    @Override
    public void initView() {
        makeStatusBarTransparent(this);
        actionBar = getSupportActionBar();
        userName = findViewById(R.id.user_name_text);
        password = findViewById(R.id.user_password_text);
        login = findViewById(R.id.login_button);
    }

    @Override
    public void initListener() {
        login.setOnClickListener(this);
    }

    @Override
    public int getContentViewID() {
        return R.layout.login_layout;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void onClick(View v) {
        String name = userName.getText().toString();
        String pw = password.getText().toString();
        SharedPreferences.Editor editor = getSharedPreferences("account",MODE_PRIVATE).edit();
        editor.putString("uid", name);
        editor.apply();
        Intent intent = new Intent(this,TableActivity.class);
        startActivity(intent);
        Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();

//        if(name.equals("123456")&&pw.equals("123")){
//            Intent intent = new Intent(this,TableActivity.class);
//            startActivity(intent);
//            Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
//        }
    }
}
