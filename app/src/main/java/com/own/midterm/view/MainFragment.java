package com.own.midterm.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.own.midterm.R;
import com.own.midterm.base.BaseFragment;
import com.own.midterm.model.Lost;
import com.own.midterm.model.UpdateLostEvent;
import com.own.midterm.presenter.LostAdaptor;
import com.own.midterm.util.BusUtil.BusUtil;
import com.own.midterm.util.BusUtil.EventUtil;
import com.own.midterm.util.BusUtil.ThreadModel;
import com.own.midterm.util.MyJSON.MyJSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.own.midterm.util.Other.makeStatusBarTransparent;

public class MainFragment extends BaseFragment{

    private RecyclerView recyclerView;

    private ImageButton release;

    private EditText searchEdit;

    private List<Lost>list = new ArrayList<>();

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyJSON myJSON = new MyJSON(this.getContext());
        myJSON.request("Sendlostproperty.php","");
        BusUtil.getDefault().register(this);

    }

    @Override
    public void initView() {
        makeStatusBarTransparent(Objects.requireNonNull(getActivity()));
        recyclerView= Objects.requireNonNull(getView()).findViewById(R.id.main_recy);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        release=getView().findViewById(R.id.release);
        release.setOnClickListener(this);
        swipeRefreshLayout=getView().findViewById(R.id.swipe_view);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MyJSON myJSON = new MyJSON(getContext());
                myJSON.request("Sendlostproperty.php","");
                BusUtil.getDefault().register(this);
            }
        });
        searchEdit = getView().findViewById(R.id.search_edit_text);
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    search();
                }
                return true;
            }
        });
    }
    void search(){
        MyJSON myJSON = new MyJSON(this.getContext());
        myJSON.request("Findlostproperty.php","?uid="+searchEdit.getText().toString());
        BusUtil.getDefault().register(this);
    }

    @Override
    public void initListener() {
    }
    @Override
    public int getContentViewID() {
        return R.layout.main_layout;
    }

    @Override
    public void destroy() {
        BusUtil.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.release:
                Intent intent = new Intent(v.getContext(),ReleaseActivity.class);
                v.getContext().startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MyJSON myJSON = new MyJSON(this.getContext());
        myJSON.request("Sendlostproperty.php", "");
        BusUtil.getDefault().register(this);

    }

    @EventUtil(threadModel = ThreadModel.MAIN)
    public void showRecyclerView(UpdateLostEvent event) {
        list = event.getLostList();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setAdapter(new LostAdaptor(list));
        swipeRefreshLayout.setRefreshing(false);
    }
}
