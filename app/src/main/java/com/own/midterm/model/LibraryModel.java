package com.own.midterm.model;

import com.own.midterm.base.BaseModel;
import com.own.midterm.contract.PlayContact;
import com.own.midterm.presenter.LibraryPresenter;

public class LibraryModel extends BaseModel<LibraryPresenter>implements PlayContact.M {

    public LibraryModel(LibraryPresenter mPresenter) {
        super(mPresenter);
    }
}
