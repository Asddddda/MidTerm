package com.own.midterm.presenter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.own.midterm.R;
import com.own.midterm.model.Recommend;
import com.own.midterm.util.Glide.MyGlide;
import com.own.midterm.util.JSON.JsonObject;
import com.own.midterm.view.ListActivity;

import org.json.JSONObject;

import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {
    private List<Recommend> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pic;
        TextView name;
        TextView song;
        View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            song = view.findViewById(R.id.playlist_name);
            name = view.findViewById(R.id.playlist_writer);
            pic = view.findViewById(R.id.playlist_pic);
        }
    }

    public RecommendAdapter(List<Recommend>list){
        mList = list;
    }

    @NonNull//三重写
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_layout,parent,false);
        final ViewHolder holder= new ViewHolder(view);
        holder.rootView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Recommend recommend = mList.get(position);
                JsonObject jsonObject = new JsonObject(view.getContext(),null);
                jsonObject.request("/playlist/detail","?id="+recommend.getId());
                Intent intent = new Intent(view.getContext(), ListActivity.class);
                Bundle data  = new Bundle();
                data.putString("imUrl",recommend.getPicUrl());
                data.putString("songName",recommend.getName());
                data.putString("creator",recommend.getCreatorName());
                intent.putExtras(data);
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recommend recommend=mList.get(position);
        MyGlide.with(holder.rootView.getContext()).load(recommend.getPicUrl()).setRatio(1f).setCompress(10).into(holder.pic);
        holder.name.setText(recommend.getCreatorName());
        holder.song.setText(recommend.getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
