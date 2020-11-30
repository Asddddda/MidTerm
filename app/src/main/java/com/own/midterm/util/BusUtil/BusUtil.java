package com.own.midterm.util.BusUtil;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *简版EventBus
 */
final public class BusUtil {
    private final Map<Class<?>, List<MethodCall>> methodCallMap = new HashMap<>();
    private final Map<Object, List<Class<?>>> unregisterMap = new HashMap<>();
    private volatile static BusUtil busUtil;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){

        public void handleMessage(Message msg){
            if(msg.what == 1) {
                Object event = msg.obj;
                int i = msg.arg1;
                List<MethodCall>methodCalls = methodCallMap.get(event.getClass());
                methodCalls.get(i).invoke(event);

            }
        }
    };


    private BusUtil() {
    }

    public static BusUtil getDefault() {
        if (busUtil == null) {
            synchronized (BusUtil.class) {
                if (busUtil == null) {
                    busUtil = new BusUtil();
                }
            }
        }
        return busUtil;
    }

    public void register(Object subscriber) {
        Class<?> clazz = subscriber.getClass();
        Method[] methods = clazz.getMethods();
        ArrayList<Object> events = new ArrayList<>();

        for (Method method : methods) {
            EventUtil eventUtil = method.getAnnotation(EventUtil.class);
            if (eventUtil != null) {
                MethodCall methodCall = new MethodCall(method, subscriber, eventUtil.threadModel());
                //这里可能会导致null pointer考虑怎么改动
                List<MethodCall> list = methodCallMap.get(method.getParameterTypes()[0]);
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(methodCall);
                events.add(method.getParameterTypes()[0]);//用不来，溜了溜了
                methodCallMap.put(method.getParameterTypes()[0],list);
            }
        }
    }

    public void post(Object event){
        try {
            List<MethodCall>methodCalls = methodCallMap.get(event.getClass());
            assert methodCalls != null;
            for (int i = 0; i<methodCalls.size(); i++){
                switch (methodCalls.get(i).getThreadModel()){
                    case MAIN:
                        if(isMainThread()) {
                            methodCalls.get(i).invoke(event);
                        }else {
                            Message msg=Message.obtain();
                            msg.what=1;
                            msg.arg1 = i;
                            msg.obj = event;
                            handler.sendMessage(msg);
                        }
                        break;
                    case DEFAULT:
                        methodCalls.get(i).invoke(event);
                        break;
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void unregister(Object subscriber) {
        try{
            List<Class<?>> list = unregisterMap.get(subscriber);
            for (Class clazz : list) {
                List<MethodCall> methodCalls = methodCallMap.get(clazz);
                if (methodCalls == null) continue;
                for (int i = methodCalls.size(); i > 0; i--) {
                    if (methodCalls.get(i).getObject() == subscriber) {
                        methodCalls.remove(i);
                    }
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public boolean isMainThread(){
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
