package com.own.midterm.view;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.own.midterm.R;
import com.own.midterm.base.BaseActivity;
import com.own.midterm.model.Lost;
import com.own.midterm.model.UpdateLostEvent;
import com.own.midterm.model.UpdateMyPostEvent;
import com.own.midterm.presenter.DelLostAdaptor;
import com.own.midterm.presenter.LostAdaptor;
import com.own.midterm.util.BusUtil.BusUtil;
import com.own.midterm.util.BusUtil.EventUtil;
import com.own.midterm.util.BusUtil.ThreadModel;
import com.own.midterm.util.GlideRoundTransform;
import com.own.midterm.util.MyJSON.MyJSON;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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