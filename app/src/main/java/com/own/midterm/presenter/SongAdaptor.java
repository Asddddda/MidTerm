package com.own.midterm.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.own.midterm.R;
import com.own.midterm.model.Song;

import java.util.List;

public class SongAdaptor extends RecyclerView.Adapter<SongAdaptor.ViewHolder>{
    private List<Song> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView songName;
        TextView songCount;
        TextView songTime;
        View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            songCount = view.findViewById(R.id.song_num);
            songName = view.findViewById(R.id.song_name);
            songTime = view.findViewById(R.id.song_time);
        }
    }

    public SongAdaptor(List<Song>list){
        mList = list;
    }

    @NonNull//三重写
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_layout,parent,false);
        final ViewHolder holder= new ViewHolder(view);
        holder.rootView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                int position=holder.getAdapterPosition();
//                Song song = mList.get(position);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song=mList.get(position);
        holder.songName.setText(song.getName());
        holder.songCount.setText(song.getCount());
        holder.songTime.setText(song.getLen());
    }

    @Override
    public int getItemCount() {
        if(mList!=null)
            return mList.size();
        else return 0;
    }

}
