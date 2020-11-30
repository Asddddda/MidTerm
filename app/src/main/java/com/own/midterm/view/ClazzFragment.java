package com.own.midterm.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.own.midterm.R;
import com.own.midterm.base.BaseFragment;
import com.own.midterm.base.CallBack;
import com.own.midterm.model.Clazz;
import com.own.midterm.model.ClazzContent;
import com.own.midterm.model.Lost;
import com.own.midterm.model.UpdateClazzEvent;

import com.own.midterm.presenter.ClazzAdaptor;
import com.own.midterm.presenter.LostAdaptor;
import com.own.midterm.util.BusUtil.BusUtil;
import com.own.midterm.util.BusUtil.EventUtil;
import com.own.midterm.util.BusUtil.ThreadModel;
import com.own.midterm.util.MyJSON.MyJSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.own.midterm.util.Other.makeStatusBarTransparent;

public class ClazzFragment extends BaseFragment{

    private int []js;

    private int loc=0;

    private int len=0;

    private List<Clazz>list;

    private List<ClazzContent>contents;

    private List<ClazzContent>contents1;

    private RecyclerView recyclerView;


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list=new ArrayList<>();
        contents=new ArrayList<>();
        contents1=new ArrayList<>();

        ClazzContent content=new ClazzContent();
        content.setContent("2100: 2    2105: 4    2107: 10  ");
        content.setFloor("1");
        contents.add(content);
        content=new ClazzContent();
        content.setContent("2214: 6 ");
        content.setFloor("2");
        contents.add(content);

        content=new ClazzContent();
        content.setContent("2305: 5    2309: 6    2311: 2    2314: 9  ");
        content.setFloor("3");
        contents.add(content);

        content=new ClazzContent();
        content.setContent("2409: 2    2411: 4    2414: 13   ");
        content.setFloor("4");
        contents.add(content);


        content=new ClazzContent();
        content.setContent("2509: 2    2510: 6    2511: 11  ");
        content.setFloor("4");
        contents.add(content);




         content=new ClazzContent();
        content.setContent("5201: 4    5202: 3    5203: 2    5204: 4    5205: 5  ");
        content.setFloor("2");
        contents1.add(content);

        content=new ClazzContent();
        content.setContent("5301: 3    5302: 3    5303: 1    5304: 9    5305: 5    5314: 6");
        content.setFloor("3");
        contents1.add(content);

        content=new ClazzContent();
        content.setContent("5601: 2    5602: 3    ");
        content.setFloor("6");

        contents1.add(content);



//        MyJSON myJSON = new MyJSON(this.getContext(), new CallBack() {
//            @Override
//            public void onSuccess() {
//                setContents();
//                //load
//            }
//
//            @Override
//            public void onFail() { }
//        });
//        myJSON.request("SendEmpty.php","");
    }

    @Override
    public void initView() {
        makeStatusBarTransparent(Objects.requireNonNull(getActivity()));
        recyclerView= Objects.requireNonNull(getView()).findViewById(R.id.clazz_recy);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setAdapter(new ClazzAdaptor(contents));


        js= new int[]{R.id.ej,R.id.sj,R.id.shj,R.id.wj,R.id.bj};
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
                recyclerView.setAdapter(new ClazzAdaptor(contents));
                loc=0;
                break;
            case R.id.sj:
                getView().findViewById(js[1]).setBackgroundResource(R.drawable.round);
                loc=1;
                break;
            case R.id.shj:
                getView().findViewById(js[2]).setBackgroundResource(R.drawable.round);
                loc=2;
                break;
            case R.id.wj:
                getView().findViewById(js[3]).setBackgroundResource(R.drawable.round);
                recyclerView.setAdapter(new ClazzAdaptor(contents1));
                loc=3;
                break;
            case R.id.bj:
                getView().findViewById(js[4]).setBackgroundResource(R.drawable.round);
                loc=4;
                break;
            default:
                break;
        }
    }

    public void setContents(){
        len=MyJSON.event.getLen();
        list=MyJSON.event.getList();
        contents.clear();

//        String temp="";
//        int flo=1;
//        ClazzContent clazzContent = new ClazzContent();
//        for(int i=0;i<len;i++){
//            if(Integer.parseInt(list.get(i).getNum().substring(0,0))==2) {
//                if(flo>Integer.parseInt(list.get(i).getNum().substring(1,1))) {
//                    clazzContent.setContent(temp);
//
//                    flo = Integer.parseInt(list.get(i).getNum().substring(1, 1));
//                    clazzContent = new ClazzContent();
//
//                    temp="";
//                    temp=temp+list.get(i).getNum()+"/"+list.get(i).getPer()+"   ";
//                }
//                else if(flo<=Integer.parseInt(list.get(i).getNum().substring(1,1))) {
//                    flo = Integer.parseInt(list.get(i).getNum().substring(1, 1));
//                    clazzContent.setContent(temp);
//                }
//                clazzContent.setFloor(list.get(i).getNum().substring(1,1)+" 楼");
//                if()
//                contents.add(clazzContent);
//            }
//        }


    }

//    public void load(){
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
//        recyclerView.setAdapter(new ClazzAdaptor(list));
//    }

}
