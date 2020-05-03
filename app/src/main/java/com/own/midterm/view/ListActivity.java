package com.own.midterm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.own.midterm.R;
import com.own.midterm.base.BaseActivity;
import com.own.midterm.base.BaseActivityPresenter;
import com.own.midterm.model.Song;
import com.own.midterm.model.UpdateRecommendEvent;
import com.own.midterm.model.UpdateSongEvent;
import com.own.midterm.presenter.RecommendAdapter;
import com.own.midterm.presenter.SongAdaptor;
import com.own.midterm.util.BusUtil.BusUtil;
import com.own.midterm.util.BusUtil.EventUtil;
import com.own.midterm.util.BusUtil.ThreadModel;

import java.util.ArrayList;
import java.util.List;

import static com.own.midterm.util.Other.makeStatusBarTransparent;

public class ListActivity extends BaseActivity {

    private RecyclerView recyclerView;

    private List<Song> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusUtil.getDefault().register(this);
    }

    @Override
    public BaseActivityPresenter getPresenterInstance() {
        return null;
    }

    @Override
    public void initView() {
//        makeStatusBarTransparent(this);
        recyclerView = findViewById(R.id.list_recy);
    }

    @Override
    public void initListener() {

    }

    @Override
    public int getContentViewID() {
        return R.layout.list_layout;
    }

    @Override
    public void destroy() {
        BusUtil.getDefault().unregister(this);
    }

    @EventUtil(threadModel = ThreadModel.MAIN)
    public void showRecyclerView(UpdateSongEvent event){
        list = event.getSongList();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new SongAdaptor(list));
    }

    @Override
    public void onClick(View v) {

    }
}
