package com.own.midterm.model;

import java.util.List;

public class UpdateClazzEvent {
    private List<Clazz> list;

    private int len;

    public List<Clazz> getList() {
        return list;
    }

    public void setList(List<Clazz> list) {
        this.list = list;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }
}
