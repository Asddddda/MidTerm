package com.own.midterm.presenter;

import com.own.midterm.base.BasePresenter;
import com.own.midterm.contract.PlayContact;
import com.own.midterm.model.LibraryModel;
import com.own.midterm.view.LibraryFragment;

public class LibraryPresenter extends BasePresenter<LibraryFragment, LibraryModel> implements PlayContact.P {
    @Override
    public LibraryModel getModelInstance() {
        return null;
    }
}
