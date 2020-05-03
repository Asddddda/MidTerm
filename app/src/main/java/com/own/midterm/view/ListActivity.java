package com.own.midterm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.own.midterm.util.Glide.MyGlide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.own.midterm.util.Other.makeStatusBarTransparent;

public class ListActivity extends BaseActivity {

    private RecyclerView recyclerView;

    private ImageView imageView;

    private TextView name;

    private TextView creator;

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
        Intent intent = getIntent();
        name = findViewById(R.id.list_name);
        creator = findViewById(R.id.list_writer);
        imageView = findViewById(R.id.list_pic);
        recyclerView = findViewById(R.id.list_recy);
        MyGlide.with(this).load(intent.getExtras().getString("imUrl")).setRatio(1f).setCompress(10).into(imageView);
        name.setText(intent.getExtras().getString("songName",""));
        creator.setText(intent.getExtras().getString("creator",""));
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
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(new SongAdaptor(list));
    }

    @Override
    public void onClick(View v) {

    }
}
