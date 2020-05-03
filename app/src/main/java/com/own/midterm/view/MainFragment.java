package com.own.midterm.view;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.own.midterm.R;
import com.own.midterm.base.BaseFragment;
import com.own.midterm.contract.MainContract;
import com.own.midterm.contract.ShowContract;
import com.own.midterm.model.Recommend;
import com.own.midterm.model.UpdateRecommendEvent;
import com.own.midterm.presenter.MainPresenter;
import com.own.midterm.presenter.RecommendAdapter;
import com.own.midterm.util.BusUtil.BusUtil;
import com.own.midterm.util.BusUtil.EventUtil;
import com.own.midterm.util.BusUtil.ThreadModel;
import com.own.midterm.util.Glide.MyGlide;

import java.util.ArrayList;
import java.util.List;

import static com.own.midterm.util.Other.makeStatusBarTransparent;

public class MainFragment extends BaseFragment<MainPresenter> implements MainContract.V{

    private RecyclerView recyclerView;

    private List<Recommend>list = new ArrayList<>();

    @Override
    public void onStart() {
        super.onStart();
        askP();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.bindView(this);
        askP();
        BusUtil.getDefault().register(this);
    }

    @Override
    public MainPresenter getPresenterInstance() {
        return new MainPresenter();
    }

    @Override
    public void initView() {
        makeStatusBarTransparent(getActivity());
        recyclerView = getView().findViewById(R.id.main_recy);
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
        this.mPresenter = null;
        BusUtil.getDefault().unregister(this);

    }

    @Override
    public void askP() {
        mPresenter.askM(getActivity());
    }

    @Override
    public void show(final String info) {
        final Context context = getActivity();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MyGlide.with(context).load(info).setCompress(10).setRatio(0.6f).into((ImageView) getActivity().findViewById(R.id.new_albums));
            }
        });
    }

    @EventUtil(threadModel = ThreadModel.MAIN)
    public void showRecyclerView(UpdateRecommendEvent event){
        list = event.getRecommendList();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(new RecommendAdapter(list));
    }

}
