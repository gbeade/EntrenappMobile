package com.example.entrenapp;

import android.content.Context;

public class ContextSingleton {
    private static ContextSingleton instance;
    private Context mContext;

    public static ContextSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new ContextSingleton(context);
        }

        return instance;
    }

    public static Context getContext(){
        if(instance==null)
            return null;
        return instance.mContext;
    }

    private ContextSingleton(Context context) {
        mContext = context;
    }
}