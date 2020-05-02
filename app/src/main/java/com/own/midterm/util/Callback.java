package com.own.midterm.util;

public interface Callback {

    void onResponse(String response);

    void onFailed(Exception e);
}