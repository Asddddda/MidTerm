package com.own.midterm.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.own.midterm.R;
import com.own.midterm.base.BaseActivity;
import com.own.midterm.base.CallBack;
import com.own.midterm.model.Lost;
import com.own.midterm.model.UpdateMyPostEvent;
import com.own.midterm.model.UpdateResEvent;
import com.own.midterm.presenter.DelLostAdaptor;
import com.own.midterm.util.BusUtil.BusUtil;
import com.own.midterm.util.BusUtil.EventUtil;
import com.own.midterm.util.BusUtil.ThreadModel;
import com.own.midterm.util.GlideRoundTransform;
import com.own.midterm.util.MyJSON.MyJSON;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.own.midterm.util.Other.makeStatusBarTransparent;

import android.util.Log;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class DemoActivity extends BaseActivity {
    private ImageView imageView;

    private String path;

    private static final int ALBUM_REQUEST_CODE = 2;

    private ImageButton demoBt;

    private File file;

    private String fileName = "";

    private ImageView imageViewRes;
    private SharedPreferences.Editor editor;

    private TextView textView;


    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(@SuppressLint("HandlerLeak") Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Toast.makeText(DemoActivity.this, "Running...", Toast.LENGTH_SHORT).show();
            }
        }
    };

    final Timer timer = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences sp = getSharedPreferences("res",MODE_PRIVATE);
        editor = getSharedPreferences("res",MODE_PRIVATE).edit();
        editor.putBoolean("waiting",true);
        editor.apply();
        demoBt =findViewById(R.id.demo_bt);
        imageView = (ImageView)findViewById(R.id.demo_pic);
        imageViewRes  = findViewById(R.id.res_pic);
        textView = findViewById(R.id.res_num);
        BusUtil.getDefault().register(this);
        getPic();
        Log.d("AFTER","");
        demoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DemoActivity.this,"开始发送...",Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        uploadMultiFile();
                    }
                }).start();
            }
        });
        timer.schedule(new TimerTask(){
                @Override
                public void run(){
                    if(sp.getBoolean("waiting",true)) {
                        Message message = new Message();
                        message.what = 0;
                        handler.sendMessage(message);
                        MyJSON myJSON = new MyJSON(DemoActivity.this, new CallBack() {
                            @Override
                            public void onSuccess() { }
                            @Override
                            public void onFail() { }
                        });
                        myJSON.request("SendDemoPhoto.php","");
                    }

                }},10000,3000);
    }

    @EventUtil(threadModel = ThreadModel.MAIN)
    public void showRes(UpdateResEvent event) {
        RequestOptions options = new RequestOptions()
                .transform(new GlideRoundTransform(this,20));
        Glide.with(this).load(event.getLink()).override(700,500)
                .centerCrop().apply(options).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE).into(imageViewRes);
        textView.setText(event.getNum());
    }

    private void getPic(){
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_PERMISSION_STORAGE = 100;
            String[] permissions = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(permissions, REQUEST_CODE_PERMISSION_STORAGE);
                    return;
                }
            }
        }
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, ALBUM_REQUEST_CODE);
    }

    /**
     * 自定义背景
     * @param requestCode
     * @param resultCode
     * @param intent 包含图片数据的intent
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode,resultCode,intent);
        if(requestCode == ALBUM_REQUEST_CODE)   //调用相册后返回
            if (resultCode == RESULT_OK) {
                //uri转path
                Uri uri = intent.getData();
                String[] arr = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(uri, arr, null, null, null);
                int img_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String img_path = cursor.getString(img_index);
                file = new File(img_path);
                path = file.getAbsolutePath();
                int start=path.lastIndexOf("/");
                int end=path.lastIndexOf(".");
                if(start!=-1 && end!=-1) {
                    fileName = path.substring(start + 1, end+4);
                }
                try{
                    FileInputStream fis = new FileInputStream(file.getAbsolutePath());
                    RequestOptions options = new RequestOptions()
                            .transform(new GlideRoundTransform(this,20));
                    Glide.with(this).load(path).override(700,500)
                            .centerCrop().apply(options).into(imageView);
                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
    }

    private void uploadMultiFile() {//将图片发送到服务器
        final String url = MyJSON.SERVER_LOC +"/zixi/Getphoto1.php";
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image1", "test.jpg", fileBody)
                .build();
        Request request = new Request.Builder().url(url)
                .post(requestBody)
                .build();
        final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = httpBuilder
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("!!!!!!", "uploadMultiFile() e=" + e);
                Toast.makeText(DemoActivity.this,"发布失败",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("!!!!!!", "uploadMultiFile() response=" + response.body().string());
            }});
    }

    @Override
    public void initView() {
        makeStatusBarTransparent(this);
    }

    @Override
    public void initListener() {

    }

    @Override
    public int getContentViewID() {
        return R.layout.demo_layout;
    }

    @Override
    public void destroy() {
        editor.putBoolean("waiting",true);
        editor.apply();
        timer.cancel();
        BusUtil.getDefault().unregister(this);
    }
    @Override
    public void onClick(View v) {

    }
}
