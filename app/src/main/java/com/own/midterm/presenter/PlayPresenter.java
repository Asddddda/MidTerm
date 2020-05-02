package com.own.midterm.presenter;

import com.own.midterm.base.BasePresenter;
import com.own.midterm.contract.PlayContact;
import com.own.midterm.contract.ShowContract;
import com.own.midterm.model.PlayModel;
import com.own.midterm.view.PlayFragment;

public class PlayPresenter extends BasePresenter<PlayFragment, PlayModel> implements PlayContact.P {
    @Override
    public PlayModel getModelInstance() {
        return null;
    }
}
