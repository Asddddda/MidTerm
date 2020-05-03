package com.own.midterm.util.BusUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodCall {
    private Method method;
    private Object object;
    private ThreadModel threadModel;
    public ThreadModel getThreadModel() { return threadModel; }
    public Object getObject(){
        return object;
    }

    public MethodCall(Method method,Object object,ThreadModel threadModel){
        this.method = method;
        this.object = object;
        this.threadModel = threadModel;
    }
    public void invoke (Object event){
        try {
            method.invoke(object,event);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
