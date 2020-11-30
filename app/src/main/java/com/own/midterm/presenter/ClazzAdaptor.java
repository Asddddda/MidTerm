package com.own.midterm.presenter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.own.midterm.R;
import com.own.midterm.model.Clazz;
import com.own.midterm.model.ClazzContent;
import com.own.midterm.model.Lost;
import com.own.midterm.util.GlideRoundTransform;

import java.util.List;

import static com.own.midterm.util.MyJSON.MyJSON.SERVER_LOC;

public class ClazzAdaptor extends RecyclerView.Adapter<ClazzAdaptor.ViewHolder> {
        private List<ClazzContent> mList;

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView floor;
            TextView content;
            View rootView;

            public ViewHolder(View view) {
                super(view);
                rootView = view;
                floor = view.findViewById(R.id.floor);
                content = view.findViewById(R.id.content);
            }
        }

        public ClazzAdaptor(List<ClazzContent> list) {
            mList = list;
        }

        @NonNull//三重写
        @Override
        public ClazzAdaptor.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clazz, parent, false);
            final ClazzAdaptor.ViewHolder holder = new ClazzAdaptor.ViewHolder(view);
            return holder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ClazzAdaptor.ViewHolder holder, int position) {
            ClazzContent clazz = mList.get(position);
            holder.floor.setText(clazz.getFloor()+"楼");
            holder.content.setText(clazz.getContent());
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
}
