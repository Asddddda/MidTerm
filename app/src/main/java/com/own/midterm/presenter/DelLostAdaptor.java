package com.own.midterm.presenter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.own.midterm.R;
import com.own.midterm.model.Lost;
import com.own.midterm.util.GlideRoundTransform;
import com.own.midterm.util.MyJSON.MyJSON;

import java.util.Date;
import java.util.List;

import static com.own.midterm.util.MyJSON.MyJSON.SERVER_LOC;


public class DelLostAdaptor extends RecyclerView.Adapter<DelLostAdaptor.ViewHolder>{
    private List<Lost> mList;

    public static boolean isPressed=false;

    public static int loc=0;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pic;
        TextView name;
        TextView time;
        TextView des;
        View rootView;
        TextView loc;
        ImageButton button;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            time=view.findViewById(R.id.lost_time2);
            des = view.findViewById(R.id.lost_des2);
            name = view.findViewById(R.id.lost_name2);
            pic = view.findViewById(R.id.lost_pic2);
            loc=view.findViewById(R.id.lost_loc2);
            button=view.findViewById(R.id.del_bt);
        }
    }

    public DelLostAdaptor(List<Lost>list){
        mList = list;
    }

    @NonNull//三重写
    @Override
    public DelLostAdaptor.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lost_reedit,parent,false);
        final DelLostAdaptor.ViewHolder holder= new DelLostAdaptor.ViewHolder(view);
        holder.button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.del_bt) {
                    if(!isPressed) {
                        isPressed = true;
                        loc = holder.getAdapterPosition();
                        Toast.makeText(view.getContext(), "确认删除吗？", Toast.LENGTH_SHORT).show();
                    }else if(loc==holder.getAdapterPosition()){
                        isPressed=false;
                        int position = holder.getAdapterPosition();
                        Lost lost = mList.get(position);
                        MyJSON myJSON = new MyJSON(view.getContext());
                        myJSON.request("Dellostproperty.php","?lpphoto="+lost.getPhoto());
                    }
                }
            }
        });
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DelLostAdaptor.ViewHolder holder, int position) {
        Lost lost=mList.get(position);
        //设置图片圆角角度
        RequestOptions options = new RequestOptions()
                .transform(new GlideRoundTransform(holder.rootView.getContext(),20));
        Glide.with(holder.rootView.getContext()).load("http://192.168.43."+SERVER_LOC+"/zixi/LostImage/"+
                lost.getPhoto()).override(700,500).centerCrop().apply(options).into(holder.pic);
//        MyGlide.with(holder.rootView.getContext()).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1586596752631&di=7a50efe18ba49d7d8a6873e98de865ec&imgtype=0&src=http%3A%2F%2Fbbs.jooyoo.net%2Fattachment%2FMon_0905%2F24_65548_2835f8eaa933ff6.jpg")
//                .setRatio(1f).setCompress(10).into(holder.pic);
        holder.name.setText("发布人: "+lost.getName());
        holder.time.setText("发布时间: "+lost.getTime());
        holder.des.setText("简述: "+lost.getDes());
        holder.loc.setText("地点: "+lost.getLoc());
        holder.button.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
