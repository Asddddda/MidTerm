package com.own.midterm.view;

import com.own.midterm.R;
import com.own.midterm.base.BaseFragment;
import com.own.midterm.contract.ShowContract;
import com.own.midterm.presenter.AccountPresenter;
import com.own.midterm.presenter.PlayPresenter;

public class PlayFragment extends BaseFragment<PlayPresenter> implements ShowContract.V {

    @Override
    public PlayPresenter getPresenterInstance() {
        return new PlayPresenter();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public int getContentViewID() {
        return R.layout.play_layout;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void askP() {

    }

    @Override
    public void showAccount() {

    }
}
