package com.own.midterm.util.JSON;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.own.midterm.contract.ShowContract;
import com.own.midterm.model.Recommend;
import com.own.midterm.model.Song;
import com.own.midterm.model.UpdateRecommendEvent;
import com.own.midterm.model.UpdateSongEvent;
import com.own.midterm.util.BusUtil.BusUtil;
import com.own.midterm.util.Glide.MyGlide;

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

public class JsonObject {

    private Context context;

    private ShowContract.M model;

    public JsonObject(Context context, ShowContract.M model){
        this.context = context;
        this.model = model;
    }

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
            case "/album/newest":
                parseBanner(response);
                break;
            case "/top/playlist":
                parseTop(response);
            case "/playlist/detail":
                parseDetail(response);
                break;
            default:
                break;
        }
    }

    private void parseAccount(String response){
        try {
            JSONObject rootObject = new JSONObject(response);
            JSONObject profileObject = rootObject.getJSONObject("profile");
            SharedPreferences.Editor editor = context.getSharedPreferences("account",MODE_PRIVATE).edit();
            editor.putString("nickname",profileObject.getString("nickname"));
            editor.putString("city",profileObject.getString("city"));
            editor.putString("follows",profileObject.getString("follows"));
            editor.putString("followeds",profileObject.getString("followeds"));
            editor.putString("eventCount",profileObject.getString("eventCount"));
            editor.apply();
            model.informP(profileObject.getString("avatarUrl"));
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void parseBanner(String response){
        Log.d("!!!!!!",response);
        try {
            JSONObject rootObject = new JSONObject(response);
            JSONArray jsonArray = rootObject.getJSONArray("albums");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            model.informP(jsonObject.getString("picUrl"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseTop(String response){
        try{
            JSONObject rootObject = new JSONObject(response);
            JSONArray jsonArray = rootObject.getJSONArray("playlists");
            UpdateRecommendEvent event = new UpdateRecommendEvent();
            List<Recommend>list = new ArrayList<>();
            for(int i = 0;i<10;i++){
                JSONObject object = jsonArray.getJSONObject(i);
                Recommend recommend = new Recommend();
                recommend.setId(object.getInt("id"));
                recommend.setName(object.getString("name"));
                recommend.setPicUrl(object.getString("coverImgUrl"));
                recommend.setCreatorName(object.getJSONObject("creator").getString("nickname"));
                list.add(recommend);
            }
            event.setRecommendList(list);
            BusUtil.getDefault().post(event);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void parseDetail(String response){
        try {
            JSONObject root = new JSONObject(response);
            JSONObject object = root.getJSONObject("playlist");
            Log.d("!!!!!!","!"+root.toString());
            JSONArray array = object.getJSONArray("tracks");
            UpdateSongEvent event = new UpdateSongEvent();
            List<Song> list = new ArrayList<>();
            int count=0;
            for(int i=0;i<array.length();i++){
                if(array.getJSONObject(i).getInt("fee")==1)
                    continue;
                Song song = new Song();
                count++;
                song.setName(array.getJSONObject(i).getString("name"));
                song.setId(array.getJSONObject(i).getInt("id"));
                song.setLen(array.getJSONObject(i).getInt("dt"));
                song.setCount(count);
                list.add(song);
            }
            event.setSongList(list);
            BusUtil.getDefault().post(event);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

}
