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
import com.own.midterm.model.Lost;
import com.own.midterm.util.GlideRoundTransform;

import java.util.List;

import static com.own.midterm.util.MyJSON.MyJSON.SERVER_LOC;


public class LostAdaptor extends RecyclerView.Adapter<LostAdaptor.ViewHolder>{
    private List<Lost> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pic;
        TextView name;
        TextView time;
        TextView des;
        View rootView;
        TextView loc;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            time=view.findViewById(R.id.lost_time);
            des = view.findViewById(R.id.lost_des);
            name = view.findViewById(R.id.lost_name);
            pic = view.findViewById(R.id.lost_pic);
            loc=view.findViewById(R.id.lost_loc);
        }
    }

    public LostAdaptor(List<Lost>list){
        mList = list;
    }

    @NonNull//三重写
    @Override
    public LostAdaptor.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lost_layout,parent,false);
        final LostAdaptor.ViewHolder holder= new LostAdaptor.ViewHolder(view);
////    holder.rootView.setOnClickListener(new View.OnClickListener(){
////            @Override
////            public void onClick(View v) {
////                int position = holder.getAdapterPosition();
////                Lost lost = mList.get(position);
////                JsonObject jsonObject = new JsonObject(view.getContext(),null);
////                jsonObject.request("/playlist/detail","?id="+recommend.getId());
////                Intent intent = new Intent(view.getContext(), ListActivity.class);
////                Bundle data  = new Bundle();
////                data.putString("imUrl",recommend.getPicUrl());
////                data.putString("songName",recommend.getName());
////                data.putString("creator",recommend.getCreatorName());
////                intent.putExtras(data);
////                view.getContext().startActivity(intent);
////            }
////        });
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LostAdaptor.ViewHolder holder, int position) {
        Lost lost=mList.get(position);
        //Log.d("!!!!!!","http://192.168.43.67/zixi/image/"+lost.getPhoto());
        //设置图片圆角角度
        RequestOptions options = new RequestOptions()
                .transform(new GlideRoundTransform(holder.rootView.getContext(),20));
        Glide.with(holder.rootView.getContext()).load(SERVER_LOC+"/zixi/LostImage/"+lost.getPhoto()).override(700,500)
                .centerCrop().apply(options).into(holder.pic);
//        MyGlide.with(holder.rootView.getContext()).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1586596752631&di=7a50efe18ba49d7d8a6873e98de865ec&imgtype=0&src=http%3A%2F%2Fbbs.jooyoo.net%2Fattachment%2FMon_0905%2F24_65548_2835f8eaa933ff6.jpg")
//                .setRatio(1f).setCompress(10).into(holder.pic);
        holder.name.setText("发布人: "+lost.getName());
        holder.time.setText("发布时间: "+lost.getTime());
        holder.des.setText("简述: "+lost.getDes());
        holder.loc.setText("地点: "+lost.getLoc());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
