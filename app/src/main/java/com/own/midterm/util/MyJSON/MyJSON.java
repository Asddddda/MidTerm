package com.own.midterm.util.MyJSON;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.own.midterm.base.CallBack;
import com.own.midterm.model.Clazz;
import com.own.midterm.model.Lost;
import com.own.midterm.model.ShowNumEvent;
import com.own.midterm.model.UpdateClazzEvent;
import com.own.midterm.model.UpdateLostEvent;
import com.own.midterm.model.UpdateMyPostEvent;
import com.own.midterm.model.UpdateResEvent;
import com.own.midterm.util.BusUtil.BusUtil;
import com.own.midterm.util.JSON.ThreadPool;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MyJSON {

    private Context context;

    public static final String SERVER_LOC = "http://124.71.148.117";

    private static String info;

    private CallBack callBack=null;

    public static UpdateClazzEvent event=new UpdateClazzEvent();

    public static UpdateResEvent resEvent=new UpdateResEvent();


    public MyJSON(Context context){
        this.context = context;
    }

    public MyJSON(Context context,CallBack callBack){
        this.context = context;
        this.callBack=callBack;
    }

    public void request(final String requestUrl, String info) {
        if(requestUrl.equals("Selflostproperty.php"+"?uid="))
            MyJSON.info =info;
        final String Url = SERVER_LOC+"/zixi/" + requestUrl + info;
        Log.d("!!!!!",Url);
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


    public void parseJSONData(String request,String response) throws JSONException {
        switch (request){
            case "Sendlostproperty.php":
            case "Findlostproperty.php":
                parseAllLost(response);
                break;
            case "Selflostproperty.php"+"?uid=":
                parseMyPost(response);
                break;
            case "SendEmpty.php":
                parseClazz(response);
                break;
            case "Dellostproperty.php":
                parseDel(response);
                break;
            case "SendDemoPhoto.php":
                parseRes(response);
                break;
            default:
                break;
        }
    }


    private void parseDel(String response) {
        request("Selflostproperty.php"+"?uid=",MyJSON.info);
    }


    private void parseRes(String response) throws JSONException {
        JSONObject root = new JSONObject(response);
        String link = root.getString("url");
        String num = root.getString("num");
        if(Integer.parseInt(num) >0){
            SharedPreferences.Editor editor = context.getSharedPreferences("res",MODE_PRIVATE).edit();
            editor.putBoolean("waiting",false);
            editor.apply();
            resEvent.setNum(num);
            resEvent.setLink(link);
            BusUtil.getDefault().post(resEvent);
            callBack.onSuccess();
        }
    }

    private void parseClazz(String response) {
        try {
            JSONArray root = new JSONArray(response);
            List<Clazz> list = new ArrayList<>();
            int len=0;
            int start;
            int len1 = root.getJSONObject(0).getInt("len");
            len=len+len1;
            for (int i = 0+1; i <= 0+len; i++) {
                Clazz clazz = new Clazz();
                clazz.setNum(root.getJSONObject(i).getString("classroom"));
                clazz.setPer(root.getJSONObject(i).getInt("number")+"");
                list.add(clazz);
            }
            start=len+2;
            len1=root.getJSONObject(start-1).getInt("len");
            len=start+len1-1;
            for (int i = start; i <= len; i++) {
                Clazz clazz = new Clazz();
                clazz.setNum(root.getJSONObject(i).getString("classroom"));
                clazz.setPer(root.getJSONObject(i).getInt("number")+"");
                list.add(clazz);
            }
            start=len+2;
            len1=root.getJSONObject(start-1).getInt("len");
            len=start+len1-1;
            for (int i = start; i <= len; i++) {
                Clazz clazz = new Clazz();
                clazz.setNum(root.getJSONObject(i).getString("classroom"));
                clazz.setPer(root.getJSONObject(i).getInt("number")+"");
                list.add(clazz);
            }
            start=len+2;
            len1=root.getJSONObject(start-1).getInt("len");
            len=start+len1-1;
            for (int i = start; i <= len; i++) {
                Clazz clazz = new Clazz();
                clazz.setNum(root.getJSONObject(i).getString("classroom"));
                clazz.setPer(root.getJSONObject(i).getInt("number")+"");
                list.add(clazz);
            }
            start=len+2;
            len1=root.getJSONObject(start-1).getInt("len");
            len=start+len1-1;
            for (int i = start; i <= len; i++) {
                Clazz clazz = new Clazz();
                clazz.setNum(root.getJSONObject(i).getString("classroom"));
                clazz.setPer(root.getJSONObject(i).getInt("number")+"");
                list.add(clazz);
            }
            MyJSON.event.setLen(len-5);
            MyJSON.event.setList(list);
            callBack.onSuccess();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseMyPost(String response) {
        try {
            JSONArray root = new JSONArray(response);
            UpdateMyPostEvent event = new UpdateMyPostEvent();
            int len = root.getJSONObject(0).getInt("len");
            SharedPreferences.Editor editor = context.getSharedPreferences(info, MODE_PRIVATE).edit();
            editor.putString("fb",len+"6666");
            editor.apply();
            SharedPreferences sp = context.getSharedPreferences("account",MODE_PRIVATE);
            if(this.callBack!=null){
                callBack.onSuccess();
            }
            List<Lost> list = new ArrayList<>();
            for (int i = 1; i <= len; i++) {
                Lost lost = new Lost();
                Log.d("!!!!!",root.getJSONObject(i).getString("uid"));
                lost.setPhoto(root.getJSONObject(i).getString("lpphoto"));
                lost.setTime(root.getJSONObject(i).getString("lptime"));
                lost.setName(root.getJSONObject(i).getString("lpfname"));
                lost.setDes(root.getJSONObject(i).getString("lpdesc"));
                lost.setLoc(root.getJSONObject(i).getString("address"));
                list.add(lost);
            }
            event.setLostList(list);
            BusUtil.getDefault().post(event);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void parseAllLost(String response) {
        try {
            JSONArray root = new JSONArray(response);
            UpdateLostEvent event = new UpdateLostEvent();
            int len = root.getJSONObject(0).getInt("len");
            List<Lost> list = new ArrayList<>();
            for (int i = 1; i <= len; i++) {
                Lost lost = new Lost();
                lost.setPhoto(root.getJSONObject(i).getString("lpphoto"));
                lost.setTime(root.getJSONObject(i).getString("lptime"));
                lost.setName(root.getJSONObject(i).getString("lpfname"));
                lost.setDes(root.getJSONObject(i).getString("lpdesc"));
                lost.setLoc(root.getJSONObject(i).getString("address"));
                list.add(lost);
            }
            event.setLostList(list);
            BusUtil.getDefault().post(event);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
