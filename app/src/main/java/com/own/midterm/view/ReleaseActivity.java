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
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.own.midterm.R;
import com.own.midterm.base.BaseActivity;
import com.own.midterm.util.BusUtil.BusUtil;
import com.own.midterm.util.GlideRoundTransform;
import com.own.midterm.util.MyJSON.MyJSON;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.own.midterm.util.Other.makeStatusBarTransparent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ReleaseActivity extends BaseActivity {
    private ImageView imageView;

    private String path;

    private static final int ALBUM_REQUEST_CODE = 2;

    private ImageButton releaseBt;

    private EditText editText;

    private EditText editText2;

    private File file;

    private String loc;

    private String des;

    private String uid;

    private String time;

    String fileName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPic();
        SharedPreferences sp = getSharedPreferences("account",MODE_PRIVATE);
        uid = sp.getString("uid","123456");
        releaseBt =findViewById(R.id.release_bt);
        imageView = (ImageView)findViewById(R.id.lost_pic_edit);
        editText=(EditText)findViewById(R.id.lost_des_edit);
        editText2=findViewById(R.id.lost_loc_edit);
        releaseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        uploadMultiFile();
                        try {
                            uploadPost();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private void uploadPost() throws IOException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日 HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        time=simpleDateFormat.format(date);
        Log.d("TIME",time);
        loc=editText2.getText().toString();
        des=editText.getText().toString();
        String jsonBody = "{\n" +
                "        \"lpphoto\": \""+fileName+"\",\n" +
                "        \"address\": \""+loc+"\",\n" +
                "        \"lpdesc\": \""+des+"\",\n" +
                "        \"lptime\": \""+time+"\",\n" +
                "        \"lpfname\": \"匿名\",\n" +
                "        \"uid\": \""+uid+"\"\n" +
                "    }";
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL url = new URL("http://192.168.43."+MyJSON.SERVER_LOC+"/zixi/Addlostproperty.php");
            URLConnection conn = url.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            // flush输出流的缓冲
            out.write(jsonBody);
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            Log.d("!!!!!!",result);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

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
        final String url = "http://192.168.43."+ MyJSON.SERVER_LOC +"/zixi/Getphoto.php";
//        file = new File( "/storage/emulated/0/", "xy.jpg");
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
//        File file2 = new File( "/storage/emulated/0/", "yyw.jpeg");
//        RequestBody fileBody2 = RequestBody.create(MediaType.parse("application/octet-stream"), file2);
        RequestBody requestBody = new MultipartBody.Builder()
        .setType(MultipartBody.FORM)
                .addFormDataPart("image1", fileName, fileBody)
//                .addFormDataPart("image2", "yyw.jpeg", fileBody2)
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
            Toast.makeText(ReleaseActivity.this,"发布失败",Toast.LENGTH_SHORT).show();
         }
         @Override
         public void onResponse(Call call, Response response) throws IOException {
             Log.i("!!!!!!", "uploadMultiFile() response=" + response.body().string());
             finish();
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
        return R.layout.release_activity;
    }

    @Override
    public void destroy() {
        BusUtil.getDefault().unregister(this);
    }
    @Override
    public void onClick(View v) {

    }
}
