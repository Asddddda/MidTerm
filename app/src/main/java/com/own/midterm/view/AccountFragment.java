package com.own.midterm.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.own.midterm.R;
import com.own.midterm.base.BaseFragment;
import com.own.midterm.contract.ShowContract;
import com.own.midterm.presenter.AccountPresenter;

import static com.own.midterm.util.Other.makeStatusBarTransparent;

public class AccountFragment extends BaseFragment<AccountPresenter> implements ShowContract.V {

    private TextView followers;

    private TextView posts;

    private TextView followings;

    private TextView name;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.bindView(this);
        askP();
    }

    @Override
    public void onStart() {
        super.onStart();
        followers = (TextView) getActivity().findViewById(R.id.followers_text);
        posts = getActivity().findViewById(R.id.posts_text);
        followings = getActivity().findViewById(R.id.following_text);
        name = getActivity().findViewById(R.id.nickname_text);
    }

    @Override
    public AccountPresenter getPresenterInstance() {
        return new AccountPresenter();
    }

    @Override
    public void initView() {
        makeStatusBarTransparent(getActivity());
    }

    @Override
    public void initListener() {

    }

    @Override
    public int getContentViewID() {
        return R.layout.account_layout;
    }

    @Override
    public void destroy() {
        this.mPresenter = null;
    }

    @Override
    public void askP() {
        mPresenter.askM(getActivity());
    }

    @Override
    public void showAccount() {
        SharedPreferences sp = this.getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
        Log.d("!!!!!!","?"+sp.getString("nickname",""));
        name.setText(sp.getString("nickname",""));
    }
}
