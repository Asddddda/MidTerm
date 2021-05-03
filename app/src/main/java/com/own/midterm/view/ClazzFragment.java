package com.own.midterm.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.own.midterm.R;
import com.own.midterm.base.BaseFragment;
import com.own.midterm.base.CallBack;
import com.own.midterm.model.Clazz;
import com.own.midterm.model.ClazzContent;


import com.own.midterm.presenter.ClazzAdaptor;

import com.own.midterm.util.CircleProgressBar;
import com.own.midterm.util.MyJSON.MyJSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.own.midterm.util.Other.makeStatusBarTransparent;

public class ClazzFragment extends BaseFragment{

    private String clazz="";

    private int []js;

    private int loc=0;

    private int len=0;

    private List<Clazz>list;

    private List<ClazzContent>contents;

    private List<ClazzContent>contents1;

    private RecyclerView recyclerView;

    private int[]table = {2,3,4,5,8};

    private CircleProgressBar circleProgressBar;

    TextView suggestText;

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     *由于没办法获取到实际的摄像头数据所以用一个自定义数据来模拟效果
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置进度条变化

        list=new ArrayList<>();
        contents=new ArrayList<>();

        MyJSON myJSON = new MyJSON(this.getContext(), new CallBack() {
            @Override
            public void onSuccess() {
                setContents();
            }

            @Override
            public void onFail() { }
        });
        myJSON.request("SendEmpty.php","");
    }

    public void exchange() {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 13);
        valueAnimator.setDuration(400);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                circleProgressBar.setProgress((int) currentValue);
            }
        });
        valueAnimator.start();
    }

    @Override
    public void initView() {
        makeStatusBarTransparent(Objects.requireNonNull(getActivity()));
        recyclerView= Objects.requireNonNull(getView()).findViewById(R.id.clazz_recy);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setAdapter(new ClazzAdaptor(contents));
        circleProgressBar = getView().findViewById(R.id.progress_bar);
        suggestText=getView().findViewById(R.id.clazz_text);
        js= new int[]{R.id.ej,R.id.sj,R.id.shj,R.id.wj,R.id.bj};//教室选择
        for(int i=0;i<5;i++)
            getView().findViewById(js[i]).setOnClickListener(this);
        getView().findViewById(js[0]).setBackgroundResource(R.drawable.round);
    }

    @Override
    public void initListener() {

    }
    @Override
    public int getContentViewID() {
        return R.layout.clazz_layout;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Objects.requireNonNull(getView()).findViewById(js[loc]).setBackgroundResource(R.drawable.white);
        switch (v.getId()){
            case R.id.ej:
                getView().findViewById(js[0]).setBackgroundResource(R.drawable.round);
                loc=0;
                setContents();
                recyclerView.setAdapter(new ClazzAdaptor(contents));
                break;
            case R.id.sj:
                getView().findViewById(js[1]).setBackgroundResource(R.drawable.round);
                loc=1;
                setContents();
                recyclerView.setAdapter(new ClazzAdaptor(contents));
                break;
            case R.id.shj:
                getView().findViewById(js[2]).setBackgroundResource(R.drawable.round);
                loc=2;
                setContents();
                recyclerView.setAdapter(new ClazzAdaptor(contents));
                break;
            case R.id.wj:
                getView().findViewById(js[3]).setBackgroundResource(R.drawable.round);
                loc=3;
                setContents();
                recyclerView.setAdapter(new ClazzAdaptor(contents));
                break;
            case R.id.bj:
                getView().findViewById(js[4]).setBackgroundResource(R.drawable.round);
                loc=4;
                setContents();
                recyclerView.setAdapter(new ClazzAdaptor(contents));
                break;
            default:
                break;
        }
        suggestText.setText(clazz);
    }

    /**
     * 将list里的数据分楼层显示
     */
    public void setContents(){
        exchange();

        len=MyJSON.event.getLen();
        list=MyJSON.event.getList();
        contents.clear();
        String temp="";
        int flo=1;

        int perNum=100;
        int if3Count=0;
        ClazzContent clazzContent = new ClazzContent();
        for(int i=0;i<len;i++){
            if(Integer.parseInt(list.get(i).getNum().substring(0,1))==table[loc]) {
                if(perNum>Integer.parseInt(list.get(i).getPer())){
                    perNum=Integer.parseInt(list.get(i).getPer());
                    clazz="    "+list.get(i).getNum();
                }
                if(flo<Integer.parseInt(list.get(i).getNum().substring(1,2))) {//新一层
                    clazzContent.setFloor(flo+" 楼");
                    clazzContent.setContent(temp);
                    contents.add(clazzContent);
                    clazzContent=new ClazzContent();
                    flo = Integer.parseInt(list.get(i).getNum().substring(1, 2));
                    temp=list.get(i).getNum()+":"+list.get(i).getPer()+"   ";
                    if(Integer.parseInt(list.get(i).getPer())<10)
                        temp+="   ";
                    if3Count=1;
                }
                else if(flo==Integer.parseInt(list.get(i).getNum().substring(1,2))){
                    temp=temp+list.get(i).getNum()+":"+list.get(i).getPer()+"   ";
                    if(Integer.parseInt(list.get(i).getPer())<10)
                        temp+="  ";
                    if3Count++;
                    if(if3Count==3){
                        if3Count=0;
                        temp+='\n';
                    }
                }
            }
        }
    }

}
