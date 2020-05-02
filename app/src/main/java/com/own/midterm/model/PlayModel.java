package com.own.midterm.model;

import com.own.midterm.base.BaseModel;
import com.own.midterm.contract.PlayContact;
import com.own.midterm.presenter.PlayPresenter;

public class PlayModel extends BaseModel<PlayPresenter>implements PlayContact.M {

    public PlayModel(PlayPresenter mPresenter) {
        super(mPresenter);
    }
}
