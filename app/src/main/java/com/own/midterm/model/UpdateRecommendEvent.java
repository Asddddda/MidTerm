package com.own.midterm.model;

import com.own.midterm.R;

import java.util.ArrayList;
import java.util.List;

/***
 * 用来通过EventBus传数据
 */

public class UpdateRecommendEvent {

    private List<Recommend> recommendList;

    public List<Recommend> getRecommendList() {
        return recommendList;
    }

    public void setRecommendList(List<Recommend> list) {
        this.recommendList = list;
    }
}
