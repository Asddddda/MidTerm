package com.own.midterm.util.JSON;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.own.midterm.contract.ShowContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

public class JsonObject {

    private Context context;

    private ShowContract.M model;

    public JsonObject(Context context, ShowContract.M model){
        this.context = context;
        this.model = model;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){

        public void handleMessage(Message msg){
            if(msg.what == 0) {
                Toast.makeText(context,"网络错误",Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void request(final String requestUrl, String info) {
        final String Url = "http://47.99.165.194" + requestUrl + info;
        ThreadPool.getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                Log.d("!!!!!!", Thread.currentThread().getName());
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(Url);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    parseJSONData(requestUrl, response.toString());
                } catch (Exception e) {
//                    if (!method.equals("find")) {
//                        Message message = Message.obtain();
//                        message.what = UPDATE_TEXT;
//                        handler.sendMessage(message);
//                    }
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        });
    }

    public void parseJSONData(String request,String response){
        switch (request){
            case "/user/detail":
                parseAccount(response);
                break;
//            case "forecast":
//                parseForecast(jsonData);
//                break;
//            case "hourly":
//                parseHourly(jsonData);
//                break;
//            case "lifestyle":
//                parseLifestyle(jsonData);
//                break;
//            case "air":
//                parseAir(jsonData);
//                break;
//            case "find":
//                parseFind(jsonData);
//                break;
            default:
                break;
        }
    }

    public void parseAccount(String response){
        try {
            Log.d("!!!!!!",response);
            JSONObject rootObject = new JSONObject(response);
            JSONObject profileObject = rootObject.getJSONObject("profile");
            SharedPreferences.Editor editor = context.getSharedPreferences("account",MODE_PRIVATE).edit();
            editor.putString("nickname",profileObject.getString("nickname"));
            Log.d("!!!!!!",profileObject.getString("nickname"));
//            editor.putString("province",profileObject.getString())
            editor.apply();
            model.informP();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

}
