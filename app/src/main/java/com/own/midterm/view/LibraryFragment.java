package com.own.midterm.view;

import com.own.midterm.R;
import com.own.midterm.base.BaseFragment;
import com.own.midterm.contract.ShowContract;
import com.own.midterm.presenter.LibraryPresenter;

import static com.own.midterm.util.Other.makeStatusBarTransparent;

public class LibraryFragment extends BaseFragment<LibraryPresenter> implements ShowContract.V {

    @Override
    public LibraryPresenter getPresenterInstance() {
        return new LibraryPresenter();
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
        return R.layout.library_layout;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void askP() {

    }

    @Override
    public void show(String info) {

    }

}
