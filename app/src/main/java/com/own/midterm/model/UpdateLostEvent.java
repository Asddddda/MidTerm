package com.own.midterm.model;

import java.util.List;

public class UpdateLostEvent {

    private List<Lost> lostList;

    public List<Lost> getLostList() {
        return lostList;
    }

    public void setLostList(List<Lost> list) {
        this.lostList = list;
    }

}
